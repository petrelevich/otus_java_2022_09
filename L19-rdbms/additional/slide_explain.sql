explain (analyze, buffers)
select cust.first_name, cust.last_name, cust.email, cntry.country
from customer cust
join address addr on cust.address_id = addr.address_id
join city cit on addr.city_id = cit.city_id
join country cntry on cit.country_id = cntry.country_id
where cntry.country = 'Russian Federation';

