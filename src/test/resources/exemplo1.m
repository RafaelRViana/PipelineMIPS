# Mostre qual a instrução do MIPS ou o menor conjunto
# de instruções do MIPS necessário para implementar o 
# comando em C que segue:
#
#			var_a = var_b + 100

.text   				# realiza operacao

la		$t0, var_a
la		$t4, var_b
lw		$t1, 0($t4)		#var_b=4
addi 	$t2, $t1, 100
sw 		$t2, 0($t0)

.data					# Data declaration section

var_a:  .word   0x0
var_b:  .word   0x4         # Constante a somar