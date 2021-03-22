PROBLEME := bin/Bani.class bin/Gard.class bin/Bomboane.class

build:	$(PROBLEME)

bin/%.class: src/%.java
	mkdir -p bin
	javac $^ -d bin

run-p1:      # nume necesar
	java -Xss128M -cp bin Bani
	
run-p2:      # nume necesar
	java -Xss128M -cp bin Gard
run-p3:      # nume necesar 
	java -Xss128M -cp bin Bomboane
run-p4:      # nume necesar 

clean:		 # nume necesar
	rm -rf bin
