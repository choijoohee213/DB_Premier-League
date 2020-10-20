use [premier league]
go

create function getTableList()
returns table
return
SELECT name AS object_name
,SCHEMA_NAME(schema_id) AS schema_name
,type
,type_desc
,create_date
,modify_date
FROM sys.objects
WHERE type='U'
--ORDER BY modify_date;