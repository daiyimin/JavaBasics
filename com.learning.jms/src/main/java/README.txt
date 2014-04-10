1. Install ActiveMQ (refer to http://activemq.apache.org/getting-started.html)
2. Check installation is OK
Login http://localhost:8161/admin/queues.jsp with admin/admin
3. Create a maven project and add ActiveMQ dependency in pom.xml
	<dependency>
		<groupId>org.apache.activemq</groupId>
		<artifactId>activemq-core</artifactId>
		<version>5.7.0</version>
	</dependency>
4. Copy source code
5. Run the program, check the queue and topic data on ActiveMQ gui