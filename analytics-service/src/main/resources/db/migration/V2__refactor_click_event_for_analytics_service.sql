ALTER TABLE click_event DROP CONSTRAINT IF EXISTS fk_click_event_mapping;

ALTER TABLE click_event RENAME COLUMN url_mapping_id TO link_id;

ALTER TABLE click_event ADD COLUMN IF NOT EXISTS short_url VARCHAR(255);

DROP INDEX IF EXISTS idx_click_event_mapping_date;

CREATE INDEX IF NOT EXISTS idx_click_event_link_id_click_date
    ON click_event(link_id, click_date);

CREATE INDEX IF NOT EXISTS idx_click_event_short_url_click_date
    ON click_event(short_url, click_date);

CREATE INDEX IF NOT EXISTS idx_click_event_click_date
    ON click_event(click_date);
