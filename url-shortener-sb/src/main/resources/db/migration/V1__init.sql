-- USERS
create table if not exists users (
    id bigserial primary key,
    email varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null,
    constraint uk_users_email unique (email),
    constraint uk_users_username unique (username)
);

-- URL MAPPING
create table if not exists url_mapping (
    id bigserial primary key,
    original_url varchar(2048) not null,
    short_url varchar(32) not null,
    click_count integer not null default 0,
    created_date timestamp not null,
    user_id bigint not null,
    constraint uk_url_mapping_short_url unique (short_url),
    constraint fk_url_mapping_user
        foreign key (user_id)
        references users(id)
        on delete cascade
);

-- INDEXES FOR URL MAPPING
create index if not exists idx_url_mapping_short_url
    on url_mapping(short_url);

create index if not exists idx_url_mapping_user_id
    on url_mapping(user_id);

create index if not exists idx_url_mapping_created_date
    on url_mapping(created_date);

-- CLICK EVENT
create table if not exists click_event (
    id bigserial primary key,
    click_date timestamp not null,
    url_mapping_id bigint not null,
    constraint fk_click_event_mapping
        foreign key (url_mapping_id)
        references url_mapping(id)
        on delete cascade
);

-- INDEXES FOR CLICK EVENT
create index if not exists idx_click_event_mapping_date
    on click_event(url_mapping_id, click_date);

create index if not exists idx_click_event_click_date
    on click_event(click_date);
