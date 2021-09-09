-- select * from system.local
-- so datacenter datacenter1 has replication factor (RF) 1
CREATE KEYSPACE IF NOT EXISTS my_keyspace
    WITH replication = {'class':'NetworkTopologyStrategy', 'datacenter1':'1'}
    AND durable_writes = true -- whether writes should be written to the commit log
;