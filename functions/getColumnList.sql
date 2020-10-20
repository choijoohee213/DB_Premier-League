use [premier league]

go
if(object_id('getColumnList')) is not null
drop function getColumnList;


go
create function getColumnList(@tname as varchar(40))
returns table
return
select schema_name(o.schema_id) as schema_n, o.name as objectname,
o.type, o.type_desc, c.name as colunm_name,
type_name(c.user_type_id) as col_type, c.max_length as length
from sys.objects as o, sys.columns as c
where o.object_id=c.object_id and o.object_id = object_id(@tname);