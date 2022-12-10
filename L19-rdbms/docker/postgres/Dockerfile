FROM postgres:10.3

COPY dvdrental.tar.bz2 /tmp/
RUN bunzip2 /tmp/dvdrental.tar.bz2

COPY 02-restore_dvdrental_db.sh /docker-entrypoint-initdb.d/

EXPOSE 5432