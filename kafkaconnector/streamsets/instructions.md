add this to docker-compose :

  streamsets:
    image: streamsets/datacollector
    ports:
      - "18630:18630"