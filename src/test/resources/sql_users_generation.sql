do language plpgsql $$
declare
	start_user_id INT;
	users INT := 1;
begin
	start_user_id:= (select max(uid)+1 from public.users_field_data);
	for i in start_user_id..(start_user_id) LOOP
 		INSERT INTO public.users_field_data (uid, langcode, preferred_langcode, preferred_admin_langcode, "name", pass, mail, timezone, status, created, changed, "access", login, init, default_langcode) VALUES(i, 'en', 'en', NULL, concat('loadtest_',i), '$2y$10$mxM8PMebElmcFQh2o/k4aOwR.anqPRgbySnaUpLIQWH8.smhYXjka', concat(floor(random() * 900000000)::text,'@example.com'), 'Europe/Budapest', 1, 1722375901, 1722376055, 1722427649, 1722427649, NULL, 1);
		INSERT INTO public.user__roles (bundle, deleted, entity_id, revision_id, langcode, delta, roles_target_id) VALUES('user', 0, i, i, 'en', 0, 'author');
		INSERT INTO public.users (uid, uuid, langcode) VALUES(i, gen_random_uuid(), 'en');
	end loop;
	commit;
end;
$$;