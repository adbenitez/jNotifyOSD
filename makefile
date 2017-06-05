PROYECT_NAME = jNotifyOSD
MAIN_CLASS = adbenitez.demo.Main
# CLASS_PATH
CP = -cp '.:assets/lib/*:bin'

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
