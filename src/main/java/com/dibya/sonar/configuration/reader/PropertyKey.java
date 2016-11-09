package com.dibya.sonar.configuration.reader;

public enum PropertyKey {
	JDBC_URL("jdbc_url"), JDBC_DRIVER("jdbc_driver"), USER_NAME("user"), PASSWORD("pass"), DIALECT("dialect"), HBM_TO_DDL(
			"hbm_to_ddl_auto"), SHOW_SQL("show_sql"), FORMAT_SQL("format_sql");

	private String key;

	private PropertyKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
