# Model-Store
REST API developed with SpringBoot for storing 3D Model Files on Amazon S3

##Features of the REST API `/api`
- Creating a User `/auth/register`
- Login as a User to obtain JWT Token for authentication of remaining APIs `/auth/login`
- Getting User info `/user/info`
- Using PostgreSQL as database for User and 3D Model file metadata `/model/list` `/model/info/{id}`
- Using Amazon S3 Bucket for file storage
- Uploading, Downloading, Updating, Deleting and Renaming of 3D Model files `/model/upload` `/model/download/{id}` `/model/delete/{id}` `/model/update/{id}`
- JWT token authentication for all the APIs
other that `/auth/*`

##Notes for running the Server on localhost
- Create a file named 'application-dev.properties' according to the template 'application.properties.template'
- Fill in the PostgreSQL and Amazon S3 configurations
- Add 'dev' profile while running the application