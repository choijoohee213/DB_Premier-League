USE [premier league]


--Lab03�� 1��_04
GO
create function getFunctionList()
returns table
return
SELECT name AS function_name
,SCHEMA_NAME(schema_id) AS schema_name
,type_desc
,create_date
,modify_date
FROM sys.objects
WHERE type_desc LIKE '%FUNCTION%';

GO


