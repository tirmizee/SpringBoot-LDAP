package com.tirmizee.repositories;

import java.util.List;

import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Repository;

import com.tirmizee.models.Person;

@Repository
public class LdapRepositoryImpl implements LdapRepository {

	@Autowired
    private LdapTemplate ldapTemplate;
	
	private static final AttributesMapper<Person> ATTRIBUTES_MAPPER = new AttributesMapper<Person>() {
		@Override
		public Person mapFromAttributes(Attributes attributes) throws javax.naming.NamingException {
			Person person = new Person();
			person.setUid((String) attributes.get("uid").get());
			person.setFullName((String) attributes.get("cn").get());
			return person;
		}
	};

	@Override
	public Person getPersonByUID(String uid) {
		LdapQuery ldapQuery = LdapQueryBuilder.query()
			.searchScope(SearchScope.SUBTREE)
			.countLimit(10)
			.attributes("sn", "cn", "uid")
			.base(LdapUtils.emptyLdapName())
            .where("objectclass").is("person")
            .and("uid").is(uid)
            .and("uid").isPresent();
		List<Person> persons = ldapTemplate.search(ldapQuery, ATTRIBUTES_MAPPER);
		return persons.isEmpty() ? null : persons.get(0);
	}
	
}
