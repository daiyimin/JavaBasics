1. Install ActiveMQ (refer to http://activemq.apache.org/getting-started.html)
Start ActiveMQ using:
\apache-activemq-5.8.0\bin>activemq

2. Check installation is OK
Login http://localhost:8161/admin/queues.jsp with admin/admin


3. Run Client
Check the ActiveMQ GUI, find that after client calls web method, it put only 1 JMS message into Queue
That means the client web call will block till return comes back.

4. Run Server
Check console, server receive the web call from JMS queue, client receive the return value

Thus JMS transport supports the async web call