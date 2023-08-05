mvn -B package --file pom.xml
rm -rf stagging
mkdir staging
cp target/*.jar staging