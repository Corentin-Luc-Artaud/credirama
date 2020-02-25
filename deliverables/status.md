Status at 06-10-2019 : We have chosen the UserStories we want to deliver for the MVP. We are creating the github issues ascociated with the UserStories, with adequate tags, milestones, etc. We are currently working on the architecture we want, meetings are planned from tomorrow. FLAG YELLOW

---

Status at 08-10-2019 : We have reviewed the stories for the analyst. There are more dashboard control oriented. We are currently specifying the roadmap and the last details of the component diagram that we will deliver tomorrox. FLAG YELLOW

---

Status at 15-10-2019 We started some pocs, and find some documentation/tutorial for kibana and elasticsearch. FLAG GREEN

---

Status at 22-10-2019 We need to progress on the integration of our pocs to get our walking skeletton ! FLAG GREEN

---

Status at 29-10-2019 Elastic search and kibana are integrated with the docker-compose. need to add a connector between kafka and ELK, and begin implementing MVP scenario code. FLAG GREEN

---

Status at 05-11-2019 Final run to finish TimeLion configurations and going to add Customer Transactions ! Also preparing presentation for Friday :). We lost a bit our group communication routine because of other projects interferences so FLAG YELLOW

---

Status at 14-01-2019 First week after the break and the updated specifications. We think we have a viable solution for the TimeZone challenge and the TimeService failure challenge. We are currently exploring viable solutions for the Faulty Database challenge, and are working to find the best solution.

- Solution for TimeZone and TimeService : we want to use a cluster of TimeService instances working together and frequently "fixing" each other using a broadcast vote system. This will also allow us to provide a locale-independant time for all of the system.

- Solution for the Faulty Database : event sourcing and CQRS are two design patterns often used together and that should solve our problem. Using these patterns will give us access to a changelog everytime the database changes, making data recovery much easier.

A more detailed explaination of these solutions is available [here](https://github.com/Corentin-Luc-Artaud/credirama/blob/master/deliverables/architecture.pdf)

What's next : we need to create new tasks and user stories to organize and split the work. FLAG GREEN

---

Status at 28-01-2020
We working on implementing/testing potential solutions. We've made some tests also for the new implementations.
FLAG GREEN

---

Status at 04-02-2020
- For database failover, we have implemented the database replication with mysql and hproxy. We will work now on replicating using a CDC, kafka and another database to compare both
- For the timezone managment, we pass all the dates with UTC format and we add a configurable local format  
FLAG GREEN

---

Status at 11-02-2020
- For Database fail-over, we have both systems, we need tests and comparison.
- For timezone management we have the implementation, we need to modify the docker-compose.yaml to add an instance of timeservice 
atomic time and test the complete infrastructure  
FLAG GREEN

---

Status at 18-02-2020
- For Database fail-over and data recovery : the third and final solution is implemented
- For timezone management: it's finished, we are testing the integration with the whole  
FLAG GREEN

---
Status at 25-02-2020
- All changes were pushed
- slides are added
- Demo videos for DB crash problematic uploaded in youtube.  

    Démo 1 : Proving that the data is replicated from kafka beteen read and writes databases https://www.youtube.com/watch?v=s4TL4Zx0brc  
    Démo 2 : Same demo than the presentation, MySQL1 crash simulation MySQL1, MySQL2 write databases, and MySQL3, MySQL4 read databases. If MySQL1 crashes, MySQL2 takes the lead for writes database and the replication via kafka is still effective/working. https://www.youtube.com/watch?v=Ap4A5O-Hx7k

FLAG GREEN
