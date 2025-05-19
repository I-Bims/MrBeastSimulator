mkdir build
javac *.java -d build
cd build
jar cfe MrBeast.jar Game *
mv MrBeast.jar ..
cd ..
java -jar MrBeast.jar
