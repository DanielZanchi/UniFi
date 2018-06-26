# Daniel Zanchi - daniel.zanchi@stud.unifi.it - consegna 30 Giugno 2015
# esercizio 2 del progetto: Procedure annidate e ricorsive
.data

str_benvenuto: .asciiz "Daniel Zanchi, Esercizio n.2 del progetto \n\n\n"
str_inserire: .asciiz "Inserire il valore n: "
str_traccia: .asciiz "Traccia:\n"
str_risultato: .asciiz "\n\nRisultato: G("
str_risultato2: .asciiz ")="
str_p_freccia: .asciiz ") --> "
str_g: .asciiz "G("
str_f: .asciiz "F("
str_f_return: .asciiz "F-return("
str_g_return: .asciiz "G-return("
str_p: .asciiz ")"
str_overflow: .asciiz "\n\nE' stato rilevato un errore di overflow\n\n"

.text

.globl main
stampa_stringa:
	li	$v0, 4	
	syscall
	jr $ra

stampa_intero:
	li $v0, 1
	syscall
	jr $ra

main:
addi $sp, $sp, -16
sw $ra, 0($sp) 	#salvo nello stack il valore di $ra, (jal main + 4)
sw $s0, 4($sp) 	#salvo nello stack il valore di $s0
sw $s1, 8($sp) 	#salvo nello stack il valore di $s1
sw $s2, 12($sp) 	#salvo nello stack il valore di $s2


la $a0, str_benvenuto 	#stampo stringa di benvenuto, con nome cognome e n esercizio
jal stampa_stringa

chiedo_n:
	la $a0, str_inserire  	#stampo stringa "inserire il valore n: "
	jal stampa_stringa
	#LEGGO IN INPUT UN NUMERO
	li $v0, 5 			# codice leggi intero = 5
	syscall 			#salva int inserito in $v0
	bltz $v0, chiedo_n 	#controllo se il valore inserito è minore di zero
	move $s2, $v0 		#salvo valore inserito in $s2

la $a0, str_traccia 	#stampo "traccia: ""
jal stampa_stringa

#stampo "G(n)-->"
la $a0, str_g 			#stampo "G("
jal stampa_stringa
move $a0, $s2 			#stampo il valore inserito in input
jal stampa_intero
la $a0, str_p_freccia 	#stampo ")-->"
jal stampa_stringa

move $a1, $s2 	#sposto il valore inserito in input in a1
jal procedura_g
	
#PROCEDURA G(N)
procedura_g:
	li $s0, 0 	#$s0 = b
	li $s1, 0 	#$s1 = k
	ciclo_for: 	#ciclo iterativo (for)
		bgt $s1, $a1, return_b 	# se k > n (inserito) allora ho finito il ciclo for
		move $a0, $s1			#sposto k in $a0, questo verrà usato dentro la procedura f(k)

		jal procedura_f  		#salto a fare la procedura F(k)

		mul $s0, $s0, $s0 		#eseguo b = b * b
		mfhi $t3 				#prendo il secondo registro da 32 bit della moltiplicazione
		beqz $t3, ok 			# se registro hi diverso da zero c'è stato un overflow, altrimenti salto e vado avanti 
		la $a0, str_overflow 	#stampo errore di overflow
		jal stampa_stringa
		j chiedo_n 					#salto per reinserire n
		ok:
			add $s0, $s0, $v0	# b = b + u, u=v0 valore di ritorno dalla ricorsione
			addi $s1, $s1, 1	#incremento k

		j ciclo_for 		#ciclo for
	return_b:
	#stampo G-return
	la $a0, str_g_return
	jal stampa_stringa
	move $a0, $s0
	jal stampa_intero
	la $a0, str_p
	jal stampa_stringa

	#stampo prima parte stringa risultato
	la $a0, str_risultato
	jal stampa_stringa
	#stampo il numero n inserito
	move $a0, $a1
	jal stampa_intero 
	#stampo seconda parte stringa risultato
	la $a0, str_risultato2
	jal stampa_stringa
	move $a0, $s0
	jal stampa_intero
	
	j end

#PROCEDURA F(N)
procedura_f: 	
	addi $sp, $sp, -8 		# crea spazio per due words nello stack frame
	sw $ra, 4($sp)	  	 	# salva l'indirizzo di ritorno al chiamante 
	sw $a0,0($sp)	   	 	# salva il parametro d'invocazione
	
	# #STAMPA F()  
	la $a0, str_f 	#stampo f(
	li $v0, 4
	syscall
	lw $a0, 0($sp) 	#stampo l'argomento di f(), prendendolo dallo stack
	li $v0, 1
	syscall
	la $a0, str_p_freccia 	#stampo )-->
	li $v0, 4
	syscall

	lw $a0, 0($sp) 		#ripristino in $a0 l'argomento di f, prendendolo dallo stack

	bnez $a0, else			# if (n != 0 ) salta a else

	#STAMPO F-RETURN CASO BASE ( n = 0 )
	#stampo il valore F-return
	la $a0, str_f_return
	li $v0, 4
	syscall
	li $a0, 1 	#stampo 1, perchè se n = 0 il valore di ritorno di f è 1. 
	li $v0, 1
	syscall
	la $a0, str_p_freccia
	li $v0, 4
	syscall
	
	lw $a0, 0($sp)		#ripristino il valore di  $a0, prendendolo dallo stack

	li $v0, 1 			# n = 0, quindi ritorna 1
	addi $sp,$sp,8     # ripristino lo stack frame
	jr $ra  	       # ritorna al chiamante

else: # n != 0
	addi $a0,$a0,-1 		#decremento il K
	jal procedura_f 	       	# chiamata ricorsiva a ricorsione (n-1)

	lw $a0,0($sp) 	 # ripristina i valori salvati in precedenza nello stack frame: parametro e indirizzo di ritorno	
	lw $ra,4($sp)	 # ripristina il registro ra dallo stack frame
	

	add $v0, $v0, $v0 		#	2*f(n-1)
	add $v0, $a0, $v0  		#	n + 2f(n-1)
	move $t0, $v0 	#salvo il valore di ritorno in s3

	#STAMPO F-RETURN CASO NON BASE
	#stampo il valore F-return
	la $a0, str_f_return
	li $v0, 4
	syscall
	move $a0, $t0
	li $v0, 1
	syscall
	la $a0, str_p_freccia
	li $v0, 4
	syscall
	
	lw $a0, 0($sp)
	lw $ra, 4($sp)
	move $v0, $t0
	addi $sp,$sp,8 	 # ripristina lo stack frame

	jr $ra		 	# ritorna al chiamante

end:

	lw $s2, 12($sp) 	#ripristino dallo stack il valore di $s2
	lw $s1, 8($sp) 	#ripristino dallo stack il valore di $s1
	lw $s0, 4($sp) 	#ripristino dallo stack il valore di $s0
	lw $ra, 0($sp) 	#ripristino dallo stack il valore di $ra
	addi $sp, $sp, 16	#ripristino lo stack pointer al suo punto iniziale
	
	li $v0, 10
	syscall