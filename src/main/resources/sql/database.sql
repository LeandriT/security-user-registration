create database "user-registration";

-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	uuid uuid NOT NULL,
	created_at timestamp(6) NULL,
	updated_at timestamp(6) NULL,
	additional_token varchar(255) NULL,
	email varchar(255) NULL,
	is_active bool NOT NULL,
	last_login timestamp(6) NULL,
	name varchar(255) NULL,
	"password" varchar(255) NULL,
	"token" varchar(255) NULL,
	CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (uuid)
);


-- public.phones definition

-- Drop table

-- DROP TABLE public.phones;

CREATE TABLE public.phones (
	uuid uuid NOT NULL,
	created_at timestamp(6) NULL,
	updated_at timestamp(6) NULL,
	city_code int4 NULL,
	country_code int4 NULL,
	"number" varchar(255) NULL,
	user_id uuid NULL,
	CONSTRAINT phones_pkey PRIMARY KEY (uuid),
	CONSTRAINT fkmg6d77tgqfen7n1g763nvsqe3 FOREIGN KEY (user_id) REFERENCES public.users(uuid)
);