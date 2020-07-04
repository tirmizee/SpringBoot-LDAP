package com.tirmizee.repositories;

import com.tirmizee.models.Person;

public interface LdapRepository {
	
	Person getPersonByUID(String uid);

}
