

package com.example.medicationapp.model

/**This file defines the Client data class as a Room entity representing clients in a medication app.
Each Client is optionally associated with a User (carer) via a foreign key relationship.

Annotations:
- @Entity marks this as a Room database table named "clients".
- foreignKeys defines a relationship with the User entity. If the related User is deleted,
the carerId in the Client table is set to NULL (SET_NULL).
- indices ensures Room creates an index on "carerId" to optimize queries involving this field.

Fields:
- clientId: Primary key, auto-generated.
- name: Client's name.
- dob: Client's date of birth.
- contact: Client's contact information.
- carerId: Optional foreign key referencing the User (carer) who is responsible for the client.

 **/


data class Client(
    val clientId: Long,

    val name: String,
    val dob: String,
    val contact: String,
    var carerId: Long?
)