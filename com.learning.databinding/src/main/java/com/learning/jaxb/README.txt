Create XML using JAXB generated classes
1) Add java jdk bin to PATH env variable
JAXB is java standard now
2) run JAXB tool(xjc) to generate java classes
com.learning.databinding\src\main\resources>xjc -p com.learning.jaxb GolfData.xsd
3) Copy generated java files to maven project
4) Run main in CreateXML.java

Create schema using schemagen of JAXB
1) Run JAXB tool schemmagen to generate schema file
com.learning.databinding\src\main\resources>schemagen -d . com\learning\jaxb\*.java
Note: Writing C:\Users\eyimdai\Dropbox\Private\workspace\sourcecode\com.learning
.databinding\src\main\resources\schema1.xsd