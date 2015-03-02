# Small POC with FoundationDB and Hibernate

## Pre-requisites

You should have an instance of FoundationDB running locally

See https://github.com/albertocsm/docker-fdb for setting up a cluster with docker

## Notes

Run the tests with
```sh
mvn test
```
Most probably the _Trips_ query will timeout as it last more then 5 secs.

Create the following index and rerun the query
```sql
CREATE INDEX idx_trips_days ON trips (day);
alter table trips update statistics idx_trips_days;
select count(id) as count from trips group by day;
```
