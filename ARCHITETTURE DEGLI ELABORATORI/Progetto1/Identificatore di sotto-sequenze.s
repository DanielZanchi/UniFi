# Daniel Zanchi - daniel.zanchi@stud.unifi.it - consegna 30 Giugno 2015
# esercizio 1 del progetto: Identificatore di sotto-sequenze
.data
ms_array: .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
ms_zero_array: .word 1, 0, 0, 0, 0, 0, 0, 0, 0, 0
c2_array: .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
c1_array: .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

buffer: .space 101

fileNotfound:	.ascii  "Il file non è stato trovato: "
file:	.asciiz	"/sequenza.txt"

str_benvenuto: .asciiz "Daniel Zanchi, Esercizio n.1 del progetto \n\n\n"
str_sequenza_sul_file:	.asciiz  "Sequenza sul file: "
str_numero_caratteri: .asciiz "Numero caratteri sul file: "
str_new_line: .asciiz "\n"
str_spazio: .asciiz "   "
str_valore_x: .asciiz "\nValore inserito: "
str_inserire_x: .asciiz "\n\nInserisci il valore di x da cercare: "
str_valore_x_minore: .asciiz "\n\nE' stato inserito un valore minore di -511"
str_valore_x_maggiore: .asciiz "\n\nE' stato inserito un valore maggiore di 511"
str_ms_array: .asciiz "\nConversione modulo e segno:     "
str_ms_zero_array: .asciiz "\nConversione modulo e segno alt: "
str_c2_array: .asciiz "\nConversione complemento a due:  "
str_c1_array: .asciiz "\nConversione complemento a uno:  "
str_occorrenza_ms: .asciiz "\nCaso MS - Elenco indici di inizio occorrenza:  "
str_occorrenza_c2: .asciiz "\nCaso C2 - Elenco indici di inizio occorrenza:  "
str_occorrenza_c1: .asciiz "\nCaso C1 - Elenco indici di inizio occorrenza:  "
str_nessuna_occorrenza: .asciiz "nessuna occorrenza"

#$s0 salvo il valore inserito in input
#s7 numero caratteri sequenza

.text

.globl main
#FUNZIONE PER ANDARE A CAPO
new_line:
	li $v0, 4
	la $a0, str_new_line
	syscall
	jr $ra


#FUNZIONE PER STAMPARE  CON INDIRIZZO IN $a0
stampa_stringa:
	li	$v0, 4		# Print String Syscall
	syscall
	jr $ra

#FUNZIONE PER STAMPARE  CON valore IN $a0
stampa_intero:
	li $v0, 1
	syscall
	jr $ra

#FUNZIONE PER STAMPARE ARRAY CON INDIRIZZO IN $t0
stampa_array:
	move $t0, $a0
	li $t5, 40
	li $t1, 0	 	#la grandezza del vettore è 40 questo mi serve pr i controlli
	stampa_int_array: 	#ciclo dopo aver salvato la variabile ra di ritorno		
		lw $a0, 0($t0) 		#salvo in $a0 l'intero contenuto nell'array ms_array
		li $v0, 1 	#stampo l'intero contenuto in a0
		syscall
		li $v0, 4
		la $a0, str_spazio 	#stampo uno spazio, per separare gli elementi
		syscall
		addi $t1, $t1, 4
		addi $t0, $t0, 4 	#incremento il mio offset di 4 per passare all'intero successivo
		blt $t1, $t5, stampa_int_array	#if $t1 (offset) < $t5 (40) salto a stampa_Array per stampare quelli successivi
		jr $ra 	#se ha finito di leggere tutto l'array posso tornare. 

#NEL CASO CHE IL VALORE INSERITO SIA MINORE DI -511 CHIEDO DI REINSERIRE IL VALORE
valore_minore:
	la $a0, str_valore_x_minore
	jal stampa_stringa
	j richiedi_input
#NEL CASO CHE IL VALORE INSERITO SIA MAGGIORE DI 511 CHIEDO DI REINSERIRE IL VALORE
valore_maggiore:
	la $a0, str_valore_x_maggiore
	jal stampa_stringa
	j richiedi_input

#FUNZIONE PER CALCOLARE IL COMPLEMENTO A DUE IN CASO L'INPUT FOSSE NEGATIVO
converti_c2_negativo:
	move $t0, $a1
	addi $t0, $t0, 1024 	#aggiungo  1024 al valore inserito da input. poi esegue la conversione in binario puro.
	move $a1, $t0
	j converti_binario 		#salto a convertire il numero $a1 in binario puro
# FUNZIONE PER CONVERTIRE IN BINARIO PURO, UN NUMERO IN $a1, SALVANDOLO IN ARRAY PARTENDO DALL'INDIRIZZO $a0 + 40 
converti_binario:
	li $t2, 2 	#$t2 = 2 per eseguire la divisione per due.
	move $t3, $a1 	#sposto il valore da dividere in $t3
	move $t0, $a0 	#metto l'indirizzo dell'array in un registro temporaneo $t0
	addi $t0, $t0, 40 	#incremento l'offset di 40 per andare in fondo all'array
	converti_binario_:
	div $t3, $t2 	#divido il mio numero inserito (o quello che è stato diviso, quoziente) per due
	mfhi $t1 	#metto il resto della divisione in $t1
	mflo $t3 	#metto il quoziente della divisione in $t3
	addi $t0, $t0, -4 	#decremento l'offset per andare alla word dell'array precedente
	sw $t1, 0($t0)	#salvo il resto della divisione appena effettuata nel vettore
	bgtz $t3, converti_binario_ 	#se il nostro quoziente è maggiore di zero torno continuo con la conversione
	jr $ra

converti_c1_negativo:
	move $t0, $a1 	# $t0 =  valore inserito da input (t0 verrà modificato)
	addi $t0, $t0, 1023 	#aggiungo 1024 al valore inserito da input. poi esegue la conversione in binario puro.
	move $a1, $t0	#salto a convertire a1 in binario puro
	j converti_binario


main:

#salvo tutti i valori dei registri che userò nel main
addi $sp, $sp, -12
sw $ra, 0($sp) 	#salvo nello stack il valore di $ra, (jal main + 4)
sw $s0, 4($sp) 	#salvo nello stack il valore di $s0
sw $s7, 8($sp) 	#salvo nello stack il valore di $s7
#stampo messaggio benvenuto
la $a0, str_benvenuto
jal stampa_stringa

# Apri File
li	$v0, 13		# Apri File Syscall
la	$a0, file 	# carica nome file
li	$a1, 0		# Read-only Flag
li	$a2, 0		# (ignored)
syscall

blt	$v0, 0, errore	# Goto Error
move	$t0, $v0	# Save File Descriptor

#leggi file
leggi:
	li	$v0, 14		# leggi File Syscall
	move $a0, $t0	# carica File Descriptor
	la	$a1, buffer	# carica Buffer Address
	li	$a2, 101	# Buffer Size (101 perchè 100 sono i caratteri massimi, e uno è la fine del file)
	syscall
	move $t2,$a1 	#metto in $t2 l'indirizzo buffer

	la	$a0, str_numero_caratteri	#stampo stringa "numeri caratteri sul file: "
	jal stampa_stringa
#conta numero caratteri
# move $t2, $t3 	#metto in $t2 l'indirizzo dove iniziare a contare, indirizzo buffer (verrà modificato)
li $t1,0 	#inializzo contatore caratteri a 0
prossimo_char: 
	lb $t0,($t2)   # leggo un carattere della stringa
    beqz $t0,stampaNumero 	# se è zero ho finito 
    add $t1,$t1,1  	# incremento il contatore caratteri
    add $t2,$t2,1  	# incremento la posizione sulla stringa
    j prossimo_char        # e continuo a leggere

stampaNumero:
	#stampo il numero di caratteri nella stringa
	move $s7, $t1 	#mi salvo su #s7 il numero di caratteri sul file, mi servirà successivamente nel programma
	move $a0, $t1
	jal stampa_intero

jal new_line

#stampo la sequenza del file
	la	$a0, str_sequenza_sul_file
	jal stampa_stringa
	la $a0, buffer 	#a0 = indirizzo sequenza su buffer
	jal stampa_stringa

# chuido il File
chiudo:
	li	$v0, 16		# Close File Syscall
	move	$a0, $t0	# Load File Descriptor
	syscall
	j	richiedi_input		# salta a richiedere il numero input

# se riscontra un errore nella lettura (es. non trova file)
errore:
	# Print String Syscall
	la	$a0, fileNotfound	# Load Error String
	jal stampa_stringa
	j end

richiedi_input:
	#RICHIEDO IN INPUT IL VALORE X
	la $a0, str_inserire_x
	jal stampa_stringa
	#LEGGO IN INPUT UN NUMERO
	li $v0, 5 			# codice leggi intero = 5
	syscall
	#CONTROLLO SE IL NUMERO IN INPUT VA BENE!
	addi $t2, $zero, -511	#metto in $t2 -512 per controllo
	blt $v0, $t2, valore_minore 	#se $v0 < -511 salto alla label "valore minore"
	addi $t2, $zero, 511	#metto in $t2 512 per controllo
	bgt $v0, $t2, valore_maggiore 	#se $v0 > 511 salto alla label "valore maggiore"

	move $s0, $v0 	#$s0 contiene il valore messo in input.

#stampo "valore inserito"
la $a0, str_valore_x
jal stampa_stringa 	

move $a0, $s0
jal stampa_intero

#CONVERTO IN MODULO E SEGNO
	la $a0, ms_array 	#carico in $t0 l'indirizzo di ms_array
	move $a1, $s0 	#sposto il valore inserito in t1 che verra poi modificato

	#FUNZIONE PER CONTROLLARE IL BIT DEL SEGNO IN MS
bit_segno:
	bgez $a1, positivo #if $a1 (input) >=0 salta a positivo	
	li $t2, 1	#carico in $t2 1 da mettere nella prima posizione del vettore nel caso il numero inserito sia negativo
	sw $t2, 0($a0) 	#metto nella prima posizione del vettore il bit 1
	abs $a1, $a1  #faccio il valore assoluto del mio numero, utile per fare operazioni successivamente, lo metto in $a1
	positivo:
	jal converti_binario 	#converto in binario puro

#CONVERTO COMPLEMENTO A DUE
	la $a0, c2_array 	#$t0 = indirizzo array complemento a due
	move $a1, $s0 	# $a1 = valore input (veràà modificato)
	bgezal $s0, converti_binario 	#in caso di numero positivo salto a converti binario puro
	bltzal $s0, converti_c2_negativo 	#in caso di numero negativo vado a modificare il numero input

finito_c2:
#CONVERTO COMPLEMENTO A UNO
	la $a0, c1_array 	# $t0 = indirizzo array complemento a uno
	move $a1, $s0 		# $a1 = valore inserito in input (verrà modificato)
	bgezal $s0, converti_binario 	#in caso di numero positivo salto a converti binario puro
	bltzal $s0, converti_c1_negativo 	#in caso di numero negativo vado a modificare il numero input

#STAMPO I NUMERI CONVERTITI
#stampo modulo e segno
la $a0, str_ms_array
jal stampa_stringa
la $a0, ms_array
jal stampa_array

#se il numero inserito in input è 0 stampo anche la conversione alternativa allo 0 in MS
bnez $s0, stampo_c2
la $a0, str_ms_zero_array
jal stampa_stringa
la $a0, ms_zero_array
jal stampa_array

stampo_c2:
#stampo complemento a due
la $a0, str_c2_array
jal stampa_stringa
la $a0, c2_array
jal stampa_array

#stampo complemento a uno
la $a0, str_c1_array
jal stampa_stringa
la $a0, c1_array
jal stampa_array


la $a0, str_new_line
jal stampa_stringa

#INIZIO CON IL CONTROLLO MS
#stampo la stringa "occorrenze MS"
la $a0, str_occorrenza_ms
jal stampa_stringa

li $t8, 0 	#rimane 0 se non viene trovata una occorrenza
li $t5, 10 	#uso per il controllo dei caratteri da controlla nella sequenza.
li $t7, 0 	#conta indice occorrenza

la $t0, buffer 	#in $1 avrò l'indirizzo della sequenza

#se l'input == 0 controllo con la sua conversione alternativa
controllo_ms_zero:
bnez $s0, controllo_ms 	# se input != 0 salto a controllo_ms

#INIZIO CON IL CONTROLLO MS se input è 0
move $t6, $s7 	# s7 = numero caratteri in sequenza
li $t7, 0 #conta indice occorrenza
li $t5, 10 	#uso per il controllo dei caratteri da controlla nella sequenza.

next_char_no_occ_zero:
	li $t9, 0 	#serve per contare il numero di occorrenze. appena arriva a 10 allora abbiamo trovato il numero nella sequenza	
	la $t2, ms_zero_array 	#in $t2 avrò l'indirizzo dell'array modulo e segno
	move $t1, $t0 	#t1 = indirizzo buffer, t1 verrà incrementato
	add $t1, $t7, $t1 	#calcolo l'offeset per leggere il carattere dal buffer
	addi $t7, $t7, 1 	#incremento indice per spostarmi nel buffer ogni volta che non viene trovata un occorrenza	
	blt $t6, $t5, controllo_ms  	#se il numero di caratteri da leggere è < di 10 salta a finito
	addi $t6, $t6, -1 	#numero caratteri = numero caratteri -1
next_char_zero:
	lb $t3, ($t1) 	#metto in $t3 il valore del carattere letto
	#adesso converto il carattere letto dal buffer e lo converto o in 1 o in 0
	converti_char_int_ms0:
		li $t4, 49 	#metto in $s5 il valore 49 che equivale allo 1 
		beq $t3, $t4, converti_in_uno_ms0 	#se $t3 (carattere letto dalla sequenza) == 49 (1) allora salta a converti_in_uno, altrimenti converte in 0
		li $t3, 0 	#adesso in $t3  abbiamo il valore 0
		j convertito_ms0
		converti_in_uno_ms0:
			li $t3, 1 	#adesso in $t3 abbiamo il valore 1
	convertito_ms0:
	lw $t4, ($t2) 	#$t4 contiene il valore letto da ms_array
	addi $t1, $t1, 1  # incremento la posizione sul buffer
	bne $t3, $t4, next_char_no_occ_zero #if $t3 != $t4 allora vado avanti con la sequenza. atrimenti sono = e posso incrementare tutti e due.	
	addi $t9, $t9, 1 #incremento il numero di occorrenze trovate, se arriva a 10 ha trovato il numero
	beq $t5, $t9, trovato_ms_zero 	#se ho trovato 10 caratteri uguali allora ho trovato un occorrenza e posso saltare a trovato_ms_zero	addi $t2, $t2, 4 	#incremento il mio offset di 4 per passare all'intero successivo del ms_array
	addi $t2, $t2, 4 	#incremento il mio offset di 4 per passare all'intero successivo del ms_array
	j next_char_zero 	#altrimenti continuo a scorrere la sequenza
trovato_ms_zero:
	li $t8, 1 	#cambio il flag a 1, ho trovato un occorrenza, utile per non stampre il messaggio "nessuna ocorrenza"
	move $a0, $t7 	#stampo l'indice di inizio della occorrenza appena trovata
	li $v0, 1
	syscall
	la $a0, str_spazio
	li $v0, 4
	syscall
	j next_char_no_occ_zero		#continuo a scorrere la sequenza

#CONTROLLO MS
controllo_ms:
move $t6, $s7 	# s7 = numero caratteri in sequenza
li $t7, 0 #conta indice occorrenza
li $t5, 10 	#uso per il controllo dei caratteri da controlla nella sequenza.


next_char_no_occ:
	li $t9, 0 	#t9 conta il numero di occorrenze. appena arriva a 10 allora abbiamo trovato il numero nella sequenza	
	la $t2, ms_array 	#in $t2 avrò l'indirizzo dell'array modulo e segno
	move $t1, $t0 	#t1 = indirizzo buffer, verrà incrementato
	add $t1, $t7, $t1 	#calcolo l'offeset per leggere il carattere dal buffer
	addi $t7, $t7, 1 	#incremento indice per spostarmi nel buffer ogni volta che non viene trovata un occorrenza
	
	blt $t6, $t5, finito_controllo_ms  	#se il numero di caratteri della sequenza da leggere è < di 10 salta a finito
	addi $t6, $t6, -1 	#numero caratteri = numero caratteri -1
next_char:
	lb $t3, ($t1) 	#metto in $t3 il valore del carattere letto
	#adesso converto il carattere letto dal buffer e lo converto o in 1 o in 0
	converti_char_int_ms:
		li $t4, 49 	#metto in $s5 il valore 49 che equivale allo 1 
		beq $t3, $t4, converti_in_uno_ms 	#se $t3 (carattere letto dalla sequenza) == 49 (1) allora salta a converti_in_uno, altrimenti converte in 0
		li $t3, 0 	#adesso in $t3  abbiamo il valore 0
		j convertito_ms
		converti_in_uno_ms:
			li $t3, 1 	#adesso in $t3 abbiamo il valore 1
	convertito_ms:

	lw $t4, ($t2) 	#$t4 contiene il valore letto da ms_array
	addi $t1, $t1, 1  # incremento la posizione sul buffer
	bne $t3, $t4, next_char_no_occ #if $t3 != $t4 allora vado avanti con la sequenza. atrimenti sono = e posso incrementare tutti e due.
	
	addi $t9, $t9, 1 #incremento il numero di occorrenze trovate, se arriva a 10 ha trovato il numero
	beq $t5, $t9, trovato_ms 	# se il numero di caratteri trovati == a 10 allora ho trovato un occorrenza e salto
	addi $t2, $t2, 4 	#incremento il mio offset di 4 per passare all'intero successivo del ms_array
	j next_char
trovato_ms:
	li $t8, 1 	#t8 = flag che ho trovato un occorrenza. utile per non stampare messaggio "nessuna occorrenza"
	move $a0, $t7 	#stampo l'indice dove è partita l'occorrenza
	li $v0, 1
	syscall
	la $a0, str_spazio
	li $v0, 4
	syscall
	j next_char_no_occ	#salto a continuare a leggere la sequenza

finito_controllo_ms:
	bnez $t8, controllo_c2 	#controllo se il flag == 0 stampo il messaggio "nessuna occorrenza", altrimenti salto a controllo_C2
	la $a0, str_nessuna_occorrenza
	li $v0, 4
	syscall

controllo_c2:
#INIZIO CON IL CONTROLLO C2
move $t6, $s7 	# s7 = numero caratteri in sequenza
li $t8, 0 	#rimane 0 se non viene trovata una occorrenza
li $t7, 0 	#conta indice occorrenza

#stampo la stringa "occorrenze C2"
la $a0, str_occorrenza_c2
li $v0, 4
syscall

next_char_no_occ_c2:
	li $t9, 0 	#serve per contare il numero di occorrenze. appena arriva a 10 allora abbiamo trovato il numero nella sequenza	
	la $t2, c2_array 	#in $2 avrò l'indirizzo dell'array complemento a 2
	move $t1, $t0  	#t1 = indirizzo buffer, verrà incrementato
	add $t1, $t7, $t1 	#calcolo l'offeset per leggere il carattere dal buffer
	addi $t7, $t7, 1 	#incremento indice per spostarmi nel buffer ogni volta che non viene trovata un occorrenza	
	blt $t6, $t5, finito_confronto_c2  	#se il numero di caratteri da leggere è < di 10 salta a finito
	addi $t6, $t6, -1 	#numero caratteri = numero caratteri -1
next_char_c2:
	lb $t3, ($t1) 	#metto in $t3 il valore del carattere letto
	#adesso converto il carattere letto dal buffer e lo converto o in 1 o in 0
	converti_char_int_c2:
		li $t4, 49 	#metto in $s5 il valore 49 che equivale allo 1 
		beq $t3, $t4, converti_in_uno_c2 	#se $t3 (carattere letto dalla sequenza) == 49 (1) allora salta a converti_in_uno, altrimenti converte in 0
		li $t3, 0 	#adesso in $t3  abbiamo il valore 0
		j convertito_c2
		converti_in_uno_c2:
			li $t3, 1 	#adesso in $t3 abbiamo il valore 1
	convertito_c2:
	lw $t4, ($t2) 	#$t4 contiene il valore letto da ms_array
	addi $t1, $t1, 1  # incremento la posizione sul buffer
	bne $t3, $t4, next_char_no_occ_c2 #if $t3 != $t4 allora vado avanti con la sequenza. atrimenti sono = e posso incrementare tutti e due.
	addi $t9, $t9, 1 #incremento il numero di caratteri trovati uguali, se arriva a 10 ha trovato un occorrenza
	beq $t5, $t9, trovato_c2 	#se s6 == 10 allora ho trovato un occorrenza
	addi $t2, $t2, 4 	#incremento il mio offset di 4 per passare all'intero successivo del ms_array
	j next_char_c2
trovato_c2:
	li $t8, 1 	# t8 = 1 per non stampare il messaggio "nessuna occorrenza"
	move $a0, $t7 	#stampo l'indice della occorrenza appena trovata
	li $v0, 1
	syscall
	la $a0, str_spazio
	li $v0, 4
	syscall
	j next_char_no_occ_c2	 #continnuo a scorrere la sequenza

finito_confronto_c2:	
	bnez $t8, controllo_c1 	#se t8 == 0 allora non ho trovato nessuna occorrenza e posso stampare il messaggio "nessuna occorrenza", altrimenti salto
	la $a0, str_nessuna_occorrenza
	li $v0, 4
	syscall

controllo_c1:
#INIZIO CON IL CONTROLLO C1
move $t6, $s7 	#s7 = numero di caratteri in sequenza
li $t8, 0 	#rimane 0 se non viene trovata una occorrenza
li $t7, 0 	#conta indice occorrenza

#stampo la stringa "occorrenze C1"
la $a0, str_occorrenza_c1
li $v0, 4
syscall 

next_char_no_occ_c1:
	li $t9, 0 	#serve per contare il numero di occorrenze. appena arriva a 10 allora abbiamo trovato il numero nella sequenza	
	la $t2, c1_array 	#in $2 avrò l'indirizzo dell'array complemento a 1
	move $t1, $t0 		#t1 = indirizzo buffer, verrà incrementato
	add $t1, $t7, $t1 	#calcolo l'offeset per leggere il carattere dal buffer
	addi $t7, $t7, 1 	#incremento indice per spostarmi nel buffer ogni volta che non viene trovata un occorrenza	
	blt $t6, $t5, finito_confronto_c1  	#se il numero di caratteri da leggere è < di 10 salta a finito
	addi $t6, $t6, -1 	#numero caratteri = numero caratteri -1
next_char_c1:
	lb $t3, ($t1) 	#metto in $t3 il valore del carattere letto
	#adesso converto il carattere letto dal buffer e lo converto o in 1 o in 0
	converti_char_int_c1:
		li $t4, 49 	#metto in $s5 il valore 49 che equivale allo 1 
		beq $t3, $t4, converti_in_uno_c1 	#se $t3 (carattere letto dalla sequenza) == 49 (1) allora salta a converti_in_uno, altrimenti converte in 0
		li $t3, 0 	#adesso in $t3  abbiamo il valore 0
		j convertito_c1
		converti_in_uno_c1:
			li $t3, 1 	#adesso in $t3 abbiamo il valore 1
	convertito_c1:
	lw $t4, ($t2) 	#$t4 contiene il valore letto da ms_array
	addi $t1, $t1, 1  # incremento la posizione sul buffer
	bne $t3, $t4, next_char_no_occ_c1 #if $t3 != $t4 allora vado avanti con la sequenza. atrimenti sono = e posso incrementare tutti e due.
	addi $t9, $t9, 1 #incremento il numero di caratteri uguali trovati, se arriva a 10 ha trovato una occorrenza 
	beq $t5, $t9, trovato_c1 	#se numero caratteri trovati uguali == 10 allora salto, perchè ho trovato una occorrenza
	addi $t2, $t2, 4 	#incremento il mio offset di 4 per passare all'intero successivo del ms_array
	j next_char_c1
trovato_c1:
	li $t8, 1 	#setto il flaf a 1 perchè ho trovato un occorrenza, per non stampre il messaggio "nessuna occorrenza"
	move $a0, $t7
	li $v0, 1
	syscall
	la $a0, str_spazio
	li $v0, 4
	syscall
	j next_char_no_occ_c1	

finito_confronto_c1:	
	bnez $t8, end 	#se non ho trovato occorrenze stampo il messaggio "nessuna ocorrenza", altirmenti salto a fine
	la $a0, str_nessuna_occorrenza
	jal stampa_stringa


end:
	lw $s7, 8($sp) 	#ripristino dallo stack il valore di $ra, (jal main + 4)
	lw $s0, 4($sp) 	#ripristino dallo stack il valore di $s0
	lw $ra, 0($sp) 	#ripristino dallo stack il valore di $s7
	addi $sp $sp, 12

	li $v0, 10 	#syscall per chiudere il programma
	syscall
