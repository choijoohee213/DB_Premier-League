--테이블 생성
use [premier league];
create table Matches(
	match_id int not null,
	stadium_id int not null,
	home_team_id int not null,
	away_team_id int not null,
	win_team_id int,
	match_round int not null,
	match_date datetime not null,
	home_team_score int default 0 not null,
	away_team_score int default 0 not null,
	match_status bit default 0 not null
	PRIMARY KEY(match_id),
	FOREIGN KEY(stadium_id) references Stadium(stadium_id) ,
	FOREIGN KEY(home_team_id) references Teams(team_id) ,
	FOREIGN KEY(away_team_id) references Teams(team_id),
	FOREIGN KEY(win_team_id) references Teams(team_id) 
	);

create table Stadium(
	stadium_id int not null,
	stadium_team_id int not null,
	stadium_name varchar(100) not null,
	stadium_capacity int not null,
	stadium_hometown varchar(100) not null,
	PRIMARY KEY(stadium_id),
	--FOREIGN KEY(stadium_team_id) references Teams(team_id)
	);
	alter table Stadium add FOREIGN KEY(stadium_team_id) references Teams(team_id);


create table Record(
	goal_id int not null,
	goal_player_id int not null,
	assisted_player_id int,
	match_id int not null,
	stadium_id int not null,
	team_id int not null,
	PRIMARY KEY(goal_id),
	FOREIGN KEY(goal_player_id) references Players(player_id) ,
	FOREIGN KEY(assisted_player_id) references Players(player_id) ,
	FOREIGN KEY(match_id) references Matches(match_id) ,
	FOREIGN KEY(team_id) references Teams(team_id) 
	);

create table Teams(
	team_id int not null,
	stadium_id int not null,
	team_name varchar(50) not null,
	team_manager varchar(50) not null,
	team_points int default 0 not null,
	team_wins int default 0 not null,
	team_draws int default 0 not null,
	team_defeats int default 0 not null,
	PRIMARY KEY(team_id),
	FOREIGN KEY(stadium_id) references Stadium(stadium_id) 
	);


create table Players(
	player_id int not null,
	player_name varchar(50) not null,
	team_id int not null,
	goals_scored int default 0 not null,
	assists int default 0 not null,
	position varchar(4) not null,
	height int,
	weight int,
	birth date,
	player_country varchar(50) not null,
	PRIMARY KEY(player_id),
	FOREIGN KEY(team_id) references Teams(team_id) 
	);


