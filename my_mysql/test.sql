select sno, sname, sage
from student
where Sdept = 'CS' or Sdept = 'MA';

select sno, sname, sage
from student
where sdept not in ('CS','MA');

select sno, sname, sage
from student
where Sdept != 'CS' and Sdept != 'MA';

select *
from student 
order by sage asc;

select sname,sage
from student
where sdept = 'CS' and sage >= 18 and sage <= 20;

select sname,sage
from student
where sdept = 'CS' and sage between 18 and  20;

select *
from student 
where sdept = 'CS' and sname like '李%';

select s.sno, s.sname, sc.grade
from student s
join sc on s.sno = sc.sno
join course c on sc.cno = c.cno
where c.cname = '数据库原理';

select s.sno, c.ccredit
from student s
join sc on s.sno = sc.sno
join course c on sc.cno = c.cno
where s.sno like '%5';

select s.sno, s.sname, sc.grade
from student s
join sc on s.sno = sc.sno
join course c on sc.cno = c.cno
where c.cname = '数据库原理' and sc.grade >= 80;

select s.sname, c.cname, sc.grade
from student s
join sc on s.sno = sc.sno
join course c on sc.cno = c.cno;

select c1.cno as '课程号', c2.cpno as '间接先行号'
from course c1
join course c2 on c1.cpno = c2.cno
where c2.cpno is not null;

select s.sname
from student s
left join sc on s.sno = sc.sno
where sc.sno is null;

select sno, avg(grade) as '平均成绩'
from sc
group by sno;

select sage, count(*) as '人数'
from student
where ssex = 'M'
group by sage
having count(*) >= 1;

select s.sname, s.sage
from student s
join (
	select sdept, max(sage) as max_age
    from student
    group by sdept
) as mage on s.sdept = mage.sdept and s.sage = mage.max_age;
    
select c.cname
from student s
join sc on s.sno = sc.sno
join course c on c.cno = sc.cno
where s.sdept = 'CS'
group by c.cno
having count(s.sno) > 5;

select sname
from student
where sno not in(
	select sno
    from sc
    join course c on sc.cno = c.cno
    where c.cname = '数据库原理');
    
select sno 
from 