package co.th.aten.network.control;

public class BasicStore {

	protected String buildSearchString(String str) {
		String searchString = "%";
		if (str != null) {
			searchString = str.trim().replace("*", "%");
			searchString = "%" + searchString + "%";
		}
		return searchString;
	}

	protected String buildOrderBy(String orderBy) {
		if (orderBy == null) {
			orderBy = "";
		}
		orderBy = orderBy.trim();
		return orderBy;
	}

}
