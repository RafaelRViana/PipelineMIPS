package br.unisc.mips;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unisc.mips.exception.InvalidInstructionException;
import br.unisc.mips.exception.UnreachableLabelException;
import br.unisc.mips.instructions.Instruction;
import br.unisc.mips.instructions.InstructionIType;
import br.unisc.mips.instructions.InstructionJType;
import br.unisc.mips.instructions.InstructionNOP;
import br.unisc.mips.instructions.InstructionRType;
import br.unisc.mips.stages.StageEX_MEM;
import br.unisc.mips.stages.StageID_EX;
import br.unisc.mips.stages.StageIF_ID;
import br.unisc.mips.stages.StageMEM_WB;

public class Datapath {

	private int programCounter = -1;

	private List<Instruction> instructionMemory;

	/**
	 * Inter-stage pipeline registers (IF/ID, ID/EX, EX/MEM, MEM/WB) save state
	 * of instruction as it propagates through the pipe.
	 * http://web.cs.dal.ca/~mheywood/CSCI3121/Pipe/06-SimplePipeRecap.pdf
	 */
	private StageIF_ID ifId = new StageIF_ID();
	private StageID_EX idEx = new StageID_EX();
	private StageEX_MEM exMem = new StageEX_MEM();
	private StageMEM_WB memWb = new StageMEM_WB();

	private Instruction currentInstruction = new InstructionNOP();

	private Map<String, Integer> labels = new HashMap<String, Integer>();

	private RegisterBank rBank = RegisterBank.getInstance();

	private DataMemory dataMemory = DataMemory.getInstance();

	private ALU alu = new ALU();

	public Datapath(List<Instruction> instructionMemory,
			Map<String, Integer> labels) {
		this.instructionMemory = instructionMemory;
		this.labels = labels;
	}

	/**
	 * The Instruction Fetch stage fetches the next instruction from memory
	 * using the address in the PC (Program Counter) register and stores this
	 * instruction in the IR (Instruction Register)
	 * https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
	 */
	public void processInstructionFetch() {

		/**
		 * The Instruction Decode phase then has two main tasks: calculate the
		 * next PC and fetch the operands for the current instruction.
		 * 
		 * There are three possibilities for the next PC: #For the most common
		 * case, for all "normal" (meaning instructions that are not branches or
		 * jumps), we must simply calculate PC+4 to get the address of the next
		 * instruction.
		 * 
		 * #For jumps and branches (if the branch is taken), we might also have
		 * to add some immediate value (the branch offset) to the PC (ADDi).
		 * This branch offset is encoded in the instruction itself.
		 * 
		 * #For the jr and jalr instructions, we need to use the value of a
		 * register instead.
		 * https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
		 */

		// Se o sinal PCSrc está ativo, o PC passa a ser o endereço de desvio calculado
		if (ifId.getPCSrc() == 1) {
			programCounter = exMem.getBranchAddress();
			ifId.setPCSrc(0);
		} else {
			programCounter++; // Nosso PC salta de um a um
		}

		/**
		 * Then, the second main task is to fetch the operands for the next
		 * instruction. These operands come from the register file for all but
		 * two instructions. The two instructions that are the exception are jal
		 * and jalr. These two jump instructions save the address of the next
		 * instruction in a destination register, so instead of sending an
		 * operand from the register file, we need to send the contents of the
		 * PC+4 (10).
		 * https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
		 */

		/**
		 * phase fetches the next instruction. First, it sends the contents of
		 * the PC register, which contains the address for the next instruction,
		 * to the instruction memory (1). The instruction memory will then
		 * respond by sending the correct instruction. This instruction is sent
		 * on to the next (instruction decode) phase (2).
		 * 
		 * The instruction decode phase will calculate the next PC and will send
		 * it back to the IF phase (4) so that the IF phase knows which
		 * instruction to fetch next. To be able to execute a jr instruction
		 * (which changes the PC to the contents of a register), we also need a
		 * connection from the register file to the PC (5).
		 */
		try {
			currentInstruction = instructionMemory.get(programCounter);
		} catch (IndexOutOfBoundsException ex) {
			currentInstruction = new InstructionNOP();
		}

		// TODO Isso seria o trabalho do Hazard Detection Unit ?

		// Se for detectado no segundo estágio que a instrução é um branch
		// a instrução seguinte, no caso esta do primeiro estágio, será um NOP
		if (idEx.isBranch()) {
			ifId.setInstructionFetched(new InstructionNOP());
			programCounter--; // PC j√° havia sido incrementado antes, e como
								// nao se sabia que ter√≠amos um NOP,
								// decrementamos ele
		} else {
			ifId.setInstructionFetched(currentInstruction);
		}
	}

	/**
	 * The Instruction Decode stage decodes the instruction in the IR,
	 * calculates the next PC, and reads any operands required from the register
	 * file. https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
	 */
	public void processInstructionDecode() {

		String ALUOp = ifId.getInstructionFetched().getOperation();

		// O PC é calculado no estágio 1

		// TODO Executar branches e saltos (jal e jalr)
		String registerDestination = "";
		Integer readData1 = 0;
		Integer readData2 = 0;
		boolean regWrite = false;
		boolean memToReg = false;
		boolean memRead = false;
		boolean memWrite = false;
		boolean branch = false;

		if ("add".equals(ALUOp)) {
			InstructionRType r = (InstructionRType) currentInstruction;
			readData1 = rBank.getValue(r.getRs());
			readData2 = rBank.getValue(r.getRt());
			registerDestination = r.getRd();
			regWrite = true;

		} else if ("addi".equals(ALUOp) || "andi".equals(ALUOp)) {
			InstructionIType i = (InstructionIType) currentInstruction;
			readData1 = rBank.getValue(i.getRs());
			readData2 = Integer.parseInt(i.getImmediate());
			registerDestination = i.getRt();
			regWrite = true;

		} else if ("beq".equals(ALUOp)) {
			InstructionIType i = (InstructionIType) currentInstruction;
			readData1 = rBank.getValue(i.getRs());
			readData2 = rBank.getValue(i.getRt());
			idEx.setLabel(i.getLabel());
			branch = true;

		} else if ("j".equals(ALUOp)) {
			InstructionJType j = (InstructionJType) currentInstruction;
			idEx.setLabel(j.getLabel());
			branch = true;

		} else if ("jal".equals(ALUOp)) {
			InstructionJType j = (InstructionJType) currentInstruction;
			registerDestination = "$ra";
			idEx.setLabel(j.getLabel());
			branch = true;
			regWrite = true;

		} else if ("jr".equals(ALUOp)) {
			InstructionRType r = (InstructionRType) currentInstruction;
			readData1 = rBank.getValue(r.getRs());
			branch = true;

		} else if ("li".equals(ALUOp)) {
			InstructionIType i = (InstructionIType) currentInstruction;
			readData1 = Integer.parseInt(i.getImmediate());
			registerDestination = i.getRt();
			regWrite = true;
			
		} else if ("lw".equals(ALUOp)) {
			InstructionIType i = (InstructionIType) currentInstruction;
			registerDestination = i.getRt();
			readData1 = rBank.getValue(i.getRt());
			readData2 = Integer.parseInt(i.getImmediate());
			regWrite = true;
			memToReg = true;
			memRead = true;
			memWrite = false;

		} else if ("sw".equals(ALUOp)) {
			InstructionIType i = (InstructionIType) currentInstruction;
			readData1 = rBank.getValue(i.getRs());
			readData2 = Integer.parseInt(i.getImmediate());
			regWrite = false;
			memToReg = false;
			memRead = false;
			memWrite = true;
			idEx.setRegRtContents(rBank.getValue(i.getRt()));

		} else if ("or".equals(ALUOp)) {
			InstructionRType r = (InstructionRType) currentInstruction;
			readData1 = rBank.getValue(r.getRs());
			readData2 = rBank.getValue(r.getRt());
			registerDestination = r.getRd();
			regWrite = true;
		}

		idEx.setInstructionFetched(ifId.getInstructionFetched());
		idEx.setALUOp(ALUOp);
		idEx.setRegisterDestination(registerDestination);
		idEx.setReadData1(readData1);
		idEx.setReadData2(readData2);
		idEx.setRegWrite(regWrite);
		idEx.setMemToReg(memToReg);
		idEx.setMemRead(memRead);
		idEx.setMemWrite(memWrite);
		idEx.setBranch(branch);
	}

	/**
	 * The Execute stage "executes" the instruction. In fact, all ALU operations
	 * are done in this stage. (The ALU is the Arithmetic and Logic Unit and
	 * performs operations such as addition, subtraction, shifts left and right,
	 * etc.)
	 * 
	 * Conceptually this stage is very simple. The ALU needs two operands. These
	 * either come from the ID phase (8, 9) and thus in turn from the register
	 * file (or PC+4 for jump and link instructions), or one operand comes from
	 * the ID phase (8) and the other comes from the instruction register (6) to
	 * supply an immediate value.
	 * https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
	 */
	public void processExecuteStage() throws UnreachableLabelException,
			InvalidInstructionException {
		int aluResult = alu.execute(idEx.getALUOp(), idEx.getReadData1(),
				idEx.getReadData2());

		exMem.setInstructionFetched(idEx.getInstructionFetched());
		exMem.setALUOut(aluResult);
		exMem.setRegisterDestination(idEx.getRegisterDestination());
		exMem.setMemToReg(idEx.isMemToReg());
		exMem.setRegWrite(idEx.isRegWrite());
		exMem.setMemRead(idEx.isMemRead());
		exMem.setMemWrite(idEx.isMemWrite());
		exMem.setBranch(idEx.isBranch());
		exMem.setRegRtContents(idEx.getRegRtContents());
		exMem.setZero(aluResult);

		// Se o resultado da ULA for 1 e for operação de branch temos que entao desviar. (BEQ, J e JAL caem aqui)
		if (exMem.getZero() == 1 && exMem.isBranch()) {
			//Calculo o endereço a partir do nome da label
			exMem.setBranchAddress(labels.get(idEx.getLabel()));
			ifId.setPCSrc(1); // Habilita a passagem do endereço no mux que escolhe o PC
		}

		if ("jr".equals(exMem.getInstructionFetched().getOperation())) {
			exMem.setBranchAddress(aluResult);
			ifId.setPCSrc(1);
		}

		// Ajusta o "PC+4" que deverá ser salvo em $ra
		if ("jal".equals(exMem.getInstructionFetched().getOperation()) || "jalr".equals(exMem.getInstructionFetched().getOperation())) {
			exMem.setALUOut(programCounter + 1);
		}
	}

	/**
	 * The Memory Access stage performs any memory access required by the
	 * current instruction, So, for loads, it would load an operand from memory.
	 * For stores, it would store an operand into memory. For all other
	 * instructions, it would do nothing.
	 * https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
	 */
	public void processMemoryAccess() {
		if (exMem.isMemRead()) {
			memWb.setMemoryData(dataMemory.read(exMem.getALUOut()));
		} else if (exMem.isMemWrite()) {
			dataMemory.write(exMem.getALUOut(), exMem.getRegRtContents());
		}

		// Porque isso?
		/*
		 * if (exMem.getZero() == 1){ programCounter = exMem.getBranchAddress();
		 * }
		 */

		memWb.setInstructionFetched(exMem.getInstructionFetched());
		memWb.setMemToReg(exMem.isMemToReg());
		memWb.setRegisterDestination(exMem.getRegisterDestination());
		memWb.setRegWrite(exMem.isRegWrite());
		memWb.setALUOut(exMem.getALUOut());
	}

	/**
	 * For instructions that have a result (a destination register), the Write
	 * Back writes this result back to the register file. Note that this
	 * includes nearly all instructions, except nops (a nop, no-op or
	 * no-operation instruction simply does nothing) and s (stores).
	 * https://www.cs.tcd.ie/Jeremy.Jones/vivio/dlx/dlxtutorial.htm
	 */
	public void processWriteBack() throws UnreachableLabelException {
		if (memWb.isMemToReg() && memWb.isRegWrite()) { // SÓ LW que tem isMemToReg como true
			rBank.setValue(memWb.getRegisterDestination(),
					memWb.getMemoryData());
		} else if (memWb.isRegWrite()) {
			rBank.setValue(memWb.getRegisterDestination(), memWb.getALUOut());
		}
	}

	public void moveForward() throws UnreachableLabelException,
			InvalidInstructionException {
		processWriteBack();
		processMemoryAccess();
		processExecuteStage();
		processInstructionDecode();
		processInstructionFetch();
	}

	public StageIF_ID getIfId() {
		return ifId;
	}

	public StageID_EX getIdEx() {
		return idEx;
	}

	public StageEX_MEM getExMem() {
		return exMem;
	}

	public StageMEM_WB getMemWb() {
		return memWb;
	}

}