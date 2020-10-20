use [premier league]

go
if(object_id('FunctionParameter')) is not null
drop function FunctionParameter;


go
create function FunctionParameter(@fname as varchar(40))
returns table
return
SELECT p.name AS parameter_name 
,SCHEMA_NAME(o.schema_id) AS schema_name
,type_name(p.user_type_id) as parameter_type
,p.max_length ,p.precision
,p.scale
,p.is_output
FROM sys.objects as o, sys.parameters as p
WHERE o.object_id = p.object_id and o.object_id = OBJECT_ID(@fname)