# Tema1PA-ParallelProgramming
[Tema1 Proiectarea Algoritmilor (2019-2020, seria CB)] 

O serie de probleme care se rezolva folosind Programare Dinamica
<br>

#### IMPLEMENTARE PROBLEMA 1 - Gigel trezorier la BNR

Problema presupune gasirea numarului de posibilitati de combinare a unui numar
de bacnote. 
- Pentru tipul 1, s-a observat ca structura impartirii bacnotelor se 
aseamana cu un arbore binar complet. De aceea, a calcula numarul de posibilitati de
impartire este echivalent cu a calcula numarul de frunze ale unui arbore binar. Prin 
urmare la tipul 1 s-a folosit formula `(2^(N-1))*5`. Pentru a calcula mai rapid puterea
lui 2 s-a folosit `Divide et Impera`.

- Pentru tipul 2 s-a folosit `Programare Dinamica`. S-a facut o matrice `dp` de [2][5].
Pe prima linie se retin valorile de la pasul `i` si pe a 2a cele de la pasul `i+1`,
primul rand fiind updatat constant. Initial pe primul rand se afla doar valori 1
(pornim cu cate o nacnota de fiecare tip). Pentru urmatorii pasi, trebuie sa ne 
uitam inapoi si sa observam de la ce bacnote putem ajunge la cea de x lei. Reculenta
difera in functie de legaturile dintre bacnote.

		- La 10lei se poate ajunge de la 50, 200 sau 500
		- La 50lei se poate ajunge de la 10 sau 200
		- La 100lei se poate ajunge de la 10, 100 sau 200
		- La 200lei se poate ajunge de la 50 sau 500
		- La 500lei se poate ajunge doar de la 200
Astfel, rezultatul reprezinta suma valorilor de pe ultimul rand din matrice.

**Complexitatea** este: 	
- `O(log n)` - tipul 1
- `O(n)` - tipul 2
<br>

#### IMPLEMENTARE PROBLEMA 2 - Gigel si Gardul

Problema presupune gasirea numarului de bucati de gard redundante. S-a creeat clasa
`Piece` care reprezinta o bucata de gard, vazuta ca un interval. Intervalele se 
salveaza intr-un ArrayList si apoi se sorteaza in functie de pozitia de start,
si eventual dupa lungimea intervalului(cele mai mari primele). Se face o alegere
`Greedy` pentru a parcurge intervalele in felul acesta. Se compara fiecare interval
cu cele care urmeaza dupa el si se elimina cele redundante, oprindu-se cand se 
gaseste un interval care are start-ul dupa end-ul intervalului curent.
Se obtine astfel numarul de bucati redundante.

**Complexitatea** difera in functie de natura intervalelor. In worst-case, complexitatea
este `O(n^2)`.
<br><br>

#### IMPLEMENTARE PROBLEMA 3 - Gigel Bombonel

Problema consta in gasirea numarului de moduri in care putem sa impartim M bomboane
la N studenti. S-a creat clasa `Interval` care retine intervalul de bomboane al fiecarui
student, salvandu-se toate intervalele intr-un ArrayList. Se face o matrice de [N][M+1]
pentru a putea folosi `Programare Dinamica`. Pe primul rand din matrice se pune 1 
corespunzator cu valorile din primul interval.
Se foloseste o reculenta pentru a calcula la pasul `i+1` in cate moduri se pot imparti
`j` bomboane considerand ce avem la pasul anterior `i`. Reculenta este: 

		dp[i+1][j] = ∑ dp[i][j - k]  unde 
			j este numarul de bomboane si 
			k este fiecare numar din intervalul persoanei i.
Rezultatul pentru M bomboane se afla pe pozitia `dp[N-1][M]`.

**Complexitatea** este:  `O(M*N)`.
