PROYECT_NAME = jNotifyOSD
MAIN_CLASS = adbenitez.test.Main
# CLASS PATH
CP = -cp '.:assets/lib/*:bin'
# to copy distribution external resources
COPY_ASSETS = echo "there aren't assets to copy."

JAVAC = javac $(CP) -d bin
JAR = jar cvmf assets/Manifest.mf dist/$(PROYECT_NAME).jar -C bin .
PROGUARD = java -jar ~/jars/proguard.jar @assets/config.pro

.PHONY: Main Proguard Rclass Rjar Mclass Mjar CLEAN CLASS_CLEAN JAR_CLEAN

Main: Mclass Rclass

Proguard:
	$(PROGUARD)

Rclass: 
	java $(CP) $(MAIN_CLASS) 

Rjar: 
	cd dist;java -jar $(PROYECT_NAME).jar

Mjar: dist JAR_CLEAN Mclass
	$(JAR)
	$(COPY_ASSETS)

Mclass: bin CLASS_CLEAN
	cp -r -t bin/ ./src/*
	find bin|grep '.java'|xargs rm
	find src|grep '.java'|xargs $(JAVAC) 

CLASS_CLEAN:
	rm -r bin/*; true

JAR_CLEAN:
	rm -r dist/*;true

CLEAN: CLASS_CLEAN JAR_CLEAN

bin:
	mkdir bin

dist:
	mkdir dist

#=====================================================================
#para listar contenido de un jar: jar tf jar-file.jar
#para extraer contenido de un jar: jar xf jar-file.jar [file-inside-jar]

