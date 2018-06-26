# Daniel Zanchi - daniel.zanchi@stud.unifi.it - consegna 30 Giugno 2015
# esercizio 3 del progetto: Operazioni fra matrici
.data
str_benvenuto: .asciiz "Daniel Zanchi, Esercizio n.3 del progetto \n"
str_menu: .asciiz "\nMenu:\na) Inserimento matrici\nb) Somma di matrici\nc) Sottrazione di matrici\nd) Prodotto di matrici\ne) Uscita dal programma\n\nScelta: "
str_a_capo: .asciiz "\n"
str_inserire_n: .asciiz "\n\nInserire grandezza matrice quadrata: "
str_matrice_scelta: .asciiz "\n\nE' stata scelta una matrice: "
str_x: .asciiz "x"
str_coda_non_vuota: .asciiz "\n\nLa coda non è vuota"
str_nuovo_elemento: .asciiz "Inserire elemento: "
str_spazio: .asciiz "\t"
str_inserimento_a: .asciiz "\nInserimento matrice A:\n"
str_inserimento_b: .asciiz "\nInserimento matrice B:\n"
str_matrice_a: .asciiz "\nMatrice A:\n"
str_matrice_b: .asciiz "\n\nMatrice B:\n"
str_a_piu_b: .asciiz "\nRisultato di A + B:\n"
str_a_meno_b: .asciiz "\nRisultato di A - B:\n"
str_a_per_b: .asciiz "\nRisultato di A X B:\n"

.text

.globl main
stampa_stringa:
	li	$v0, 4		# syscall 4 stampa stringa con indirizzo in $a0
	syscall
	jr $ra

stampa_intero:
	li $v0, 1 		# syscall 1 stampa intero contenuto in $a0
	syscall
	jr $ra

#INSERISCI MATRICE (scelta a)
inserisci_matrice:
	addi $sp, $sp, -4
	sw $ra, 0($sp) 	#salvo il valore di ritorno $ra nello stack
	chiedi_grandezza:
		la $a0, str_inserire_n 		#stampo messaggio "inserire grandezza matrice: "
		jal stampa_stringa

		li $v0, 5 			# leggo n
		syscall 			#salva int inserito in $v0
		blez $v0, chiedi_grandezza 	#se v0 < = 0 salta a richiedere n, altrimenti vado avanti
		move $t5, $v0 		#sposto il valore delle righe inserite in $t5
		addi $sp, $sp, -4
		sw $t5, 0($sp) 		#salvo nello stack la grandezza della mia matrice

		#stampo messaggio "matrice scelta n x n"
		la $a0, str_matrice_scelta 
		li $v0, 4
		syscall
		move $a0, $t5
		li $v0, 1
		syscall
		la $a0, str_x
		li $v0, 4
		syscall
		move $a0, $t5
		li $v0, 1
		syscall	 
		la $a0, str_a_capo
		li $v0, 4
		syscall

		
		la $a0, str_inserimento_a 	#stampo messaggio "inserimento matrice a:"
		li $v0, 4
		syscall

		mul $t1, $t5, $t5 	#t1 contiene il numero di celle necessarie per la matrice

		move $a1, $t1 		#$a1 contiene il numero di celle necessarie per la mia matrice
		move $a0, $a2	 	#$a0 = testa matrice A, verrà modificato
		jal riempi_matrice 	#salto a inserire elementi matrice A
		addi $sp, $sp, -4
		sw $v0, 0($sp) 		#salvo nello stack la testa della  matrice A

		la $a0, str_inserimento_b 	#stampo messaggio "inserimento matrice b:"
		li $v0, 4
		syscall 	

		lw $t5, 4($sp) 			#carico dallo stack la grandezza della mia matrice
		mul $t1, $t5, $t5 		# t1 = numero di celle necessarie
		move $a1, $t1			#a1 numero celle necessarie per la matrice
		move $a0, $a3		 	#$a0 testa matrice B
		jal riempi_matrice 	#stampo a inserire elementi matrice B
		addi $sp, $sp, -4
		sw $v0, 0($sp) 		#salvo nello stack la testa della  matrice B
		lw $t0, 12($sp)		#ripristino dallo stack il valore di ra, per tornare al chiamante (jal insesci_matrici + 4)
		jr $t0

		riempi_matrice: 		#testa della mia matrice contenuta in a0
			move $t3, $a0		#sposto la mia testa in t3
			move $t2, $a1 		#sposto numero di blocchi necessari in t2
			addi $sp, $sp, -4
			sw $ra, 0($sp) 		#salvo nello stack il valore di $ra, (jal main + 4)
			move $t4, $zero 	#inizializzo il contatore di blocchi inseriti a 0
			move $t8, $t3
			riempi_matrice_:
				addi $t4, $t4, 1 	#incremento contatore elementi inseriti
				bgt $t4, $t2, matrice_piena 	#se il mio contatore di blocchi inseriti è >= a il numero dei blocchi necessari per la matrice salto a stampare la matrice
				
				move $t0, $t3 		#mi muovo alla cella successiva
				beqz $t3, crea_cella	#se t3 == 0 salto a creare una nuova cella di memoria, altrimenti utilizzo le celle già create

				la $a0, str_nuovo_elemento 		#stampo messaggio "inserire nuovo elemento"
				li $v0, 4
				syscall

				li $v0, 5
				syscall                       # legge un intero
				move $t1 $v0                  # t1=input

				lw $t3, 4($t0) 		# $t3 = campo elemento successivo
				
				sw $t1, 0($t0) 		# campo intero = $t1
				move $t9, $t0 		# t9 = coda
				
				beqz $t3, crea_cella_incr 	#se t3 (che contiene l'indirizzo dell'elemento successivo) == 0 allora ho finito le celle già craete e salto a crearne nuove
				j riempi_matrice_ 		#altrimenti vado avanti con le celle già create

				crea_cella_incr:
					addi $t4, $t4, 1 	#incremento contatore elementi inseriti
				crea_cella:		
					bgt $t4, $t2, inserito 	#se il mio contatore di blocchi inseriti è >= a il numero dei blocchi necessari per la matrice salto a stampare la matrice
					la $a0, str_nuovo_elemento 		#stampo messaggio "inserire nuovo elemento"
					li $v0, 4
					syscall

					li $v0, 5
					syscall                       # legge un intero
					move $t1 $v0                  # t1 = input

					li $v0, 9 		# inizio inserzione nuovo elemento (chiamata sbrk)
					li $a0, 8 		#crea un blocco di 8 byte
					syscall         # chiamata sbrk: restituisce un blocco di 8 byte, puntato da v0: il nuovo record
					# vegono riempiti i due campi del nuovo record:
					sw $t1, 0($v0)              	# campo intero = t1
					sw $zero, 4($v0)              	# campo elemento-successivo = nil

					bne $t8, $zero, collega_ultimo       	# se t8!=nil (coda non vuota) vai a collega_ultimo
					move $t8, $v0       # altrimenti (prima inserzione)  Testa=Coda=v0, $t8 sarà la testa della matrice
					move $t3, $v0		# aggiorno la testa della mia matrice
					move $t9, $v0		# aggiorno la coda della mia matrice

					addi $t4, $t4, 1 	#incremento contatore elementi inseriti
					j crea_cella        # torna all'inizio del loop di input, per inserire altri elementi

					collega_ultimo:         # se la coda e' non vuota, collega l'ultimo elemento della lista,
											# puntato da Coda (t9) al nuovo record; dopodiche' modifica Coda per farlo puntare al nuovo record
					sw $v0, 4($t9)          # il campo elemento successivo dell'ultimo del record prende v0
					move $t9, $v0           # Coda = v0, aggiorno la coda all'ultimo elemento inserito

					addi $t4, $t4, 1 		#incremento contatore elementi inseriti
					j crea_cella            # torna all'inizio del loop di input, per inserire altri elementi
				matrice_piena:
					sw $zero, 4($t0)		#in caso di matrice riempita metto a zero il campo elemento successivo dell'ultimo elemento
				inserito:
					move $v0, $t8
					lw $ra, 0($sp) 		#ripristino dallo stack il valore di $ra
					addi $sp, $sp, 4	#ripristino lo stack pointer al suo punto iniziale	
					jr $ra					#salto all'indirizzo del registro salvato in precedenza, per andare a stampre la matrice

#SOMMA MATRICI (scelta b)
somma:
	move $t0, $a0 	#sposto la testa della matrice a in t0
	move $t1, $a1 	#sposto la testa della matrice B in t1
	somma_nuova_riga:
	la $a0, str_a_capo 	#stampo "\n"
	li $v0, 4
	syscall
	move $t4, $zero  	#setto il contatore delle colonne a 0
	loop_somma:
		beq $t0, $zero, somma_eseguita		# se  t0 == 0 si e' raggiunta la fine della matrice e salto a menu
		beq $t4, $a2, somma_nuova_riga 	#se s2(numero colonne stampate)== a2 (numero colonne matrice) salto a nuova riga, dobbiamo andare a capo.
		addi $t4, $t4, 1		# incremento contatore colonne stampate
		lw $t2, 0($t0)			# t2 = valore del campo intero matrice A
		lw $t3, 0($t1) 		 	# t3 = valore del campo interno matrice B
		add $a0, $t2, $t3 		# A[i] + B[i] = $a0
		li $v0, 1				# stampo la somma appena ottenuta
		syscall
		li $v0, 4				# stampo uno spazio, per separare un elemento dall'altro
		la $a0, str_spazio
		syscall
		lw $t0, 4($t0)			# t0 = valore del campo elemento-successivo dell'elemento corrente matrice A
		lw $t1, 4($t1) 			# t1 = valore del campo elemento-successivo dell'elemento corrente matrice b
		j loop_somma			# continuo a sommare le due matrici
	somma_eseguita:
		jr $ra

#SOTTRAI MATRICI (scelta c)
sottrai:
	move $t0, $a0 	#t0 testa matrice A
	move $t1, $a1 	#t1 testa matrice B 
	sottrai_nuova_riga:
		la $a0, str_a_capo 	#stampo "\n", per andare a capo
		li $v0, 4
		syscall
		move $t4, $zero  	#setto il contatore delle colonne a 0
		loop_sottrai:
			beq $t0, $zero, sottrazione_eseguita		# se  t0 == 0 si e' raggiunta la fine della matrice e si salto al menu
			beq $t4, $a2, sottrai_nuova_riga 	#se s2 (numero colonne stampate) == a2 (numero colonne matrice) salto a nuova riga, dobbiamo andare a capo.
			addi $t4, $t4, 1		#incremento contatore colonne stampate
			lw $t2, 0($t0)			# t2 = valore del campo intero matrice A
			lw $t3, 0($t1) 		 	# t3 = valore del campo interno matrice B
			sub $a0, $t2, $t3 		# A[i] - B[i] = $a0 
			li $v0, 1				# stampo la sottrazione appena ottenuta
			syscall
			li $v0, 4 				# stampo lo spazio per separare un elemento dall'altro
			la $a0, str_spazio
			syscall
			lw $t0, 4($t0)			# t0 = valore del campo elemento-successivo dell'elemento corrente matrice A
			lw $t1, 4($t1) 			# t1 = valore del campo elemento-successivo dell'elemento corrente matrice A
			j loop_sottrai			# continuo a sottrarre le matrici
		sottrazione_eseguita:
			jr $ra

#MOLTIPLICA MATRICI (scelta d)
moltiplica:
	addi $sp, $sp, -12
	sw $ra, 0($sp)
	sw $s7, 4($sp)
	sw $s3, 8($sp)
	move $t0, $a1   	#t0 = testa matrice A temporanea
	move $t1, $a2 		#t1 = testa matrice B temporanea
	move $t9, $zero 	#t9 = contatore per sapere a che elemento mi trovo

	mul_nuova_riga: 	
		la $a0, str_a_capo 	#stampo "\n" per andare a capo ogni volta che moltiplico una riga.
		li $v0, 4
		syscall
		move $s7, $zero 	# contatore che metto a zero, conta il numero di colonne moltiplicate
		loop_moltiplica:
			beq $t0, $zero, matrici_moltiplicate		# se  t0 == 0 si e' raggiunta la fine della matrice e si esce
			beq $s7, $a3, mul_nuova_riga 	#se s7 == a3 salto a nuova riga, dobbiamo andare a capo.			
			move $s3, $zero 			#registro che uso per operazioni, conterrà il risultato
			div $t3, $t9, $a3 			# t3 = numero della riga in cui mi trovo
			addi $t9, $t9, 1			# incremento contatore, mi dice a che elemento della matrice mi trovo
			addi $s7, $s7, 1			# incremento contatore, mi dice a quale colonna della riga mi trovo
			move $t5, $a2 		# t5 = testa matrice B
			move $t6, $zero 	# t6 = contatore per sapere se sono alla colonna giusta
			addi $t4, $s7, -1 	# $t4 = numero colonnea in cui mi trovo, viene decrementata per il controllo sottostante
			posizionati_alla_colonna: 	#posiziona $t5 in cima alla colonna in cui mi trovo.
				beq $t4, $t6, colonna_trovata 	#se contatore colonne t6 == alla colonna in cui mi trovo t4, ho trovato la colonna per cui moltiplicare
				addi $t6, $t6, 1 	#incremento il contatore colonne
				lw $t5, 4($t5) 		#t5 = campo elemento successivo di t5
				j posizionati_alla_colonna
			colonna_trovata:
			move $t6, $zero 	# t6 = contatore per sapre in che riga mi trovo
			move $t4, $s5 		# t4 = testa matrice A
				posizionati_alla_riga: 	#posiziona t4 al primo elemento della riga per cui devo moltiplicare
					beq $t3, $t6, riga_trovata 	#se riga in cui mi trovo $t3 ==  contatore righe, ho trovato la riga per cui moltiplicare.
					addi $t6, $t6, 1 	#incremento il contatore delle righe
					move $t7, $zero 	#conto colonna, appena arriva al numero di colonne allora sono alla riga dopo
					scorri_riga: 
						addi $t7, $t7, 1 	#incrementa il numero di colonna					
						lw $t4, 4($t4) 		#t4 = campo elemento successivo di matrice A
						beq $t7, $a3, posizionati_alla_riga 	#se contatore colonna $t7 == numero colonne della matrice $a3, posso andare alla riga dopo
						j scorri_riga		#se non sono arrivato in fondo alla riga, continuo a ciclare
				riga_trovata:
				move $t8, $zero 	#contatore elementi moltiplicati e stampati
				scorri:
					addi $t8, $t8, 1	#incremento il contatore elemetni
	 				lw $t3, 0($t4) 		#elemento matrice A, $t3 = A[i]
	 				lw $t6, 0($t5) 		#elemento matrice B, $t6 = B[i]
	 				mul $t6, $t3, $t6 	# t6 = A[i] X B[i]
	 				add $s3, $s3, $t6  	#somma di prodotti, somma gli elementi appena moltiplicati più quelli precedenti
	 				beq $a3, $t8, stampa_elemento 	#se contatore elementi == grandezza matrice allora salta a stampare il risultato. 
	 				lw $t4, 4($t4) 	#passo all'elemento successivo nella matrice A	
	 				move $t7, $zero 	#contatore colonne, per andare alla riga successiva
					riga_succ: 	#mette in t5 l'indirizzo della cella sottostante. va a alla riga dopo.
						addi $t7, $t7, 1		#incremento contatore colonne. 								 						
						lw $t5, 4($t5) 		#t5 = campo elemento successivo di t4
						beq $t7, $a3, scorri 	#se contatore colonne == grandezza matrice torna sopra a effettuare le operazioni di moltiplicazione e addizione
						j riga_succ 	#altrimenti continua a scorrere la matrice per andare alla riga sotto. 

				stampa_elemento: 	#stampa il risultato ottenuto da righe per colonne.
					move $a0, $s3 	# $a0 == risultato da stampare
	 				li $v0, 1 	#stampa intero
					syscall
					li $v0, 4  	#stampo spazio per mettere accanto elementi
					la $a0, str_spazio
					syscall

				lw $t0, 4($t0) 		# vado all'elemento successivo da moltiplicare. 
				j loop_moltiplica 	#salto e continuo a moltiplicare
			matrici_moltiplicate:
				lw $s3, 8($sp)
				lw $s7, 4($sp)
				lw $t0, 0($sp)
				addi $sp, $sp, 12
				jr $t0	

stampa_matrice: # a0 = Testa. t0 verra' usato come puntatore per scorrere gli elementi della matrice, $a1 grandezza matrice
	move $t0, $a0		
	nuova_riga: 	#stampo un "\n" per andare a capo
		la $a0, str_a_capo
		li $v0, 4
		syscall
		move $t2, $zero  	#setto il contatore delle colonne a 0
		loop_print:
			beq $t0, $zero, matrice_stampata	# se  t0 == 0 si e' raggiunta la fine della matrice e si esce
			beq $t2, $a1, nuova_riga 	#se il contatore t2 == grandezza matrice (n colonne) allora salto a stampare nuova riga
			addi $t2, $t2, 1 		#incremento contatore delle colonne stampate
			li $v0, 1				# 1 = syscall per stampare intero
			lw $a0, 0($t0)			# a0 = valore del campo intero dell'elemento corrente (puntato da t0)
			syscall					# stampa valore intero dell'elemento corrente
			li $v0, 4				# stampo uno spazio, per separare un elemento dall'altro
			la $a0, str_spazio			
			syscall					
			lw $t0, 4($t0)			# t0 = valore del campo elemento-successivo dell'elemento corrente (puntato da t0)
			j loop_print			#salto e continuo a stampare la matrice
		matrice_stampata:
		jr $ra 		#salto all'indirizzo contenuto nel registro ra

main:
addi $sp, $sp, -12
sw $ra, 0($sp) 	#salvo nello stack il valore di $ra, (jal main + 4)
sw $s0, 4($sp) 	#salvo nello stack il valore di $s0
sw $s5, 8($sp) 	#salvo nello stack il valore di $s5
sw $s6, 12($sp) 	#salvo nello stack il valore di $s6

move $s5, $zero 	#testa matrice A inizializzata a 0, verrà modificata dopo aver inserito la prima matrice
move $s6, $zero		#testa matrice b inizializzata a 0, verrà modificata dopo aver inserito la prima matrice

la $a0, str_benvenuto 	#stampo stringa di benvenuto
jal stampa_stringa 
menu:
#stampo il menu
la $a0, str_a_capo
jal stampa_stringa
la $a0, str_menu
jal stampa_stringa

#richiedo una lettera in input
li $v0, 12	#12 legge char
syscall 	#carattere = $v0
move $t0, $v0 	#sposto il mio carattere della scelta in $t0

#confronto il carattere inserito con le varie lettere per poi saltare alla funzione scelta
li $t1, 'a'
beq $t0, $t1, scelta_a

li $t1, 'b'
beq $t0, $t1, scelta_b

li $t1, 'c'
beq $t0, $t1, scelta_c

li $t1, 'd'
beq $t0, $t1, scelta_d

li $t1, 'e'
beq $t0, $t1, scelta_e

j menu
#funzioni scelta A


scelta_a:

	move $a2, $s5 	#a2 = testa matrice A (se già creata in precedenza)
	move $a3, $s6	#a3 = testa matrice B (se già creata in precedenza)
	jal inserisci_matrice	
	
	lw $s6, 0($sp) 			#ripristino dallo stack la testa della matrice B
	lw $s5, 4($sp) 			#ripristino dallo stack la testa della matrice A
	lw $s0, 8($sp) 			#ripristino dallo stack la grandezza delle matrici
	addi $sp, $sp, 16

	matrici_inserite:
		#stampo le matrici
		la $a0, str_matrice_a
		jal stampa_stringa
		move $a0, $s5 	#sposto la testa della matrice A in a0
		move $a1, $s0 	#sposto la grandezza della matrice in a1
		jal stampa_matrice
		la $a0, str_matrice_b
		jal stampa_stringa
		move $a0, $s6 	#sposto la testa della matrice B in a0
		move $a1, $s0 	#sposto la grandezza della matrice in a1
		jal stampa_matrice

	j menu 	# salta al menu

scelta_b:
	#$s5 testa matrice A
	#$s6 testa matrice B
	#$s0 numero colonne

	la $a0, str_a_piu_b 	#stampo il messaggio "Risultato di A + B: "
	jal stampa_stringa

	move $a0, $s5 	#sposto la testa della matrice A in $a0
	move $a1, $s6	#sposto la testa della matrice B in $a1
	move $a2, $s0  	#sposto la grandezza delle matrici in $a2
	jal somma
	j menu

scelta_c:
	#$s5 testa matrice A
	#$s6 testa matrice B
	#$s0 numero colonne
	la $a0, str_a_meno_b 	#stampo il messaggio "risultato A - B : "
	jal stampa_stringa

	move $a0, $s5 		#a0 = testa matrice A, verrà modificato
	move $a1, $s6		#t1 = testa matrice B, verrà modificato
	move $a2, $s0  	#sposto la grandezza delle matrici in $a2
	jal sottrai
	j menu
 
scelta_d:
	#$s5 testa matrice A
	#$s6 testa matrice B
	#$s0 n colonne
	la $a0, str_a_per_b 	#stampo messaggio "risultato A x B: "
	jal stampa_stringa

	move $a1, $s5 		#a1 testa matrice A
	move $a2, $s6 		#a2 testa matrice B
	move $a3, $s0		#a3 grandezza matrici
	jal moltiplica
	j menu


scelta_e:

	lw $s6, 12($sp) 	#ripristino dallo stack il valore di s6
	lw $s5, 8($sp) 		#ripristino dallo stack il valore di $s5
	lw $s0, 4($sp) 		#ripristino  dallo stack il valore di $s0
	lw $ra, 0($sp) 		#ripristino dallo stack il valore di $ra, (jal main + 4)
	addi $sp, $sp, 12

	li $v0, 10 	#syscall per uscita da sistema
	syscall

