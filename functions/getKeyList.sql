use [premier league]

go
if(object_id('getKeyList')) is not null
drop function getKeyList;


go
create function getKeyList()
returns table
return
select schema_name(schema_id) as schema_name,name,type,type_desc, object_id, parent_object_id from sys.key_constraints
union all
select schema_name(schema_id) as schema_name,name,type,type_desc, object_id, parent_object_id from sys.foreign_keys
go
