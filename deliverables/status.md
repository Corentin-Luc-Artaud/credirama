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
