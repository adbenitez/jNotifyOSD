PROYECT_NAME = jNotifyOSD
MAIN_CLASS = adbenitez.demo.Main
# CLASS_PATH
CP = -cp '.:assets/lib/*:bin'

JAVAC = javac -source 1.5 -target 1.5 $(CP) -d bin
JAR = jar cvmf assets/Manifest.mf dist/$(PROYECT_NAME).jar -C bin .
PROGUARD = java -jar ~/jars/proguard.jar @assets/config.pro

.PHONY: Main proguard rclass rjar mclass mjar CLEAN CLASS_CLEAN JAR_CLEAN

Main: mclass rclass

proguard:
	$(PROGUARD)

rclass:
	java $(CP) $(MAIN_CLASS)

rjar:
	cd dist;java -jar $(PROYECT_NAME).jar

mjar: dist JAR_CLEAN mclass
	$(JAR)
	$(COPY_ASSETS)

mclass: bin CLASS_CLEAN
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
