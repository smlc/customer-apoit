CREATE TABLE appointment
(
    id uuid primary key NOT NULL,
    start_datetime_at timestamp with time zone NOT NULL,
    end_datetime_at   timestamp with time zone NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    user_email text,
    user_phone text,
    services text NOT NULL,
    status text  NOT NULL,
    place text NOT NULL
);

