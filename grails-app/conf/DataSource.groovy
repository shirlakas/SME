dataSource {
	pooled = false
	driverClassName = "com.mysql.jdbc.Driver"
	//username = "pfm"
	//password = "pfm"
	username = "shirley"
	password = ""
	//username = "root"
	//password = ""
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			pooled = true
	/*		driverClassName = "org.hsqldb.jdbcDriver"
			username = "sa"
			password = ""
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
			url = "jdbc:mysql://localhost/PFM_dev"*/
			dbCreate = "create-drop"
			
			url = "jdbc:mysql://localhost/pfm_new"
		}
	}
	test {
		dataSource {
			dbCreate = "create-drop"
			url = "jdbc:mysql://localhost/pfm_new"
		}
	}
	production {
		dataSource {
			dbCreate = "create-drop"
			url = "jdbc:mysql://localhost/pfm_new"
		}
	}
}
