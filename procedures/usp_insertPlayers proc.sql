use [premier league]
go
if object_id('usp_insertPlayers') is not null
drop proc usp_insertPlayers

go
create proc usp_insertPlayers 
@i1 int,
@c1 varchar(50), 
@i2 int, 
@i3 int = 0,
@i4 int = 0,
@c2 varchar(4),
@i5 int,
@i6 int,
@d1 date,
@c3 varchar(50)
as 
begin
declare @i int
select @i = count(player_id) from dbo.Players
where player_id=@i1;

if @i>0
raiserror('Record already exists..',16,1);
else
insert into dbo.Players
values (@i1,@c1,@i2,@i3,@i4,@c2,@i5,@i6,convert(datetime,@d1),@c3)
end


