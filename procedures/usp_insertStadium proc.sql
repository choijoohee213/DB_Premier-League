use [premier league]
go
if object_id('usp_insertStadium') is not null
drop proc usp_insertStadium

go
create proc usp_insertStadium 
@i1 int,
@i2 int,  
@i3 int,
@c1 varchar(100),
@c2 varchar(100)
as 
begin
declare @i int
select @i = count(stadium_id) from dbo.Stadium
where stadium_id=@i1;

if @i>0
raiserror('Record already exists..',16,1);
else
insert into dbo.Stadium
values (@i1,@i2,@i3,@c1,@c2)
end

/*
exec usp_insertStadium 13,12,22222,'London','stadium'
select * from dbo.Stadium;
*/