package hotel.ejb.controller.datatable;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import hotel.dao.CrudDAO;
import hotel.dao.Page;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.TableData;
import hotel.ejb.services.DatatableService.Value;

public abstract class BaseDataTableController<ID extends Serializable, EntityClass> {

	protected TableData data;
	protected Page page = Page.builder().first(0).max(10).build();
	protected CrudDAO<ID, EntityClass> crudDAO;
	protected EntityClass queryDTO;

	public void setCrudDao(CrudDAO<ID, EntityClass> crudDAO) {
		this.crudDAO = crudDAO;
	}
	
	abstract public void beforeQueryAction();
	
	public TableData fillDatatableAndGet() {
		beforeQueryAction();
		return fillDatatableAndGet(crudDAO.findByQuery(queryDTO));
	}

	public TableData fillDatatableAndGet(List<EntityClass> values) {
		data = new TableData();
		data.setTabHeaders(getHeader());
		data.setRows(createRows(values));

		return data;
	}

	abstract protected List<String> getHeader();

	public List<Row> createRows(List<EntityClass> values) {
		return values.stream().map(this::createRow).collect(Collectors.toList());
	}

	abstract protected Row createRow(EntityClass obj);

	public TableData getData() {
		return data;
	};

	public Value prepareValue(Object object) {
		Value value = new Value();
		if (object != null)
			value.setValue(object.toString());
		else
			value.setValue("");

		return value;
	}
	
	abstract public String redirectToQueryResult();

	public EntityClass getQueryDTO() {
		return queryDTO;
	}
	
	public void setQuery(EntityClass queryDTO) {
		this.queryDTO = queryDTO;
	}
}
