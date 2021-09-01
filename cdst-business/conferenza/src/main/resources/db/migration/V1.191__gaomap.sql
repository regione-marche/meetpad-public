ALTER TABLE cdst.conferenza ADD COLUMN geomap_guid character varying(50);
CREATE UNIQUE INDEX conferenza_geomap_guid_idx ON cdst.conferenza (geomap_guid);