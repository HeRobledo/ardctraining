package com.ardctraining.core.product.dao.impl;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.dao.CustomProductLabelDao;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

public class DefaultCustomProductLabelDao implements CustomProductLabelDao {

    private FlexibleSearchService flexibleSearchService;

    private static final Logger LOG = Logger.getLogger(DefaultCustomProductLabelDao.class);

    private static final String SELECT =
            "SELECT {" + ItemModel.PK + "} " +
            "FROM   {" + CustomProductLabelModel._TYPECODE + "} ";
    private static final String FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_QUERY =
            SELECT +
            "WHERE  {" + CustomProductLabelModel.CUSTOMER + "} = ?customer AND " +
            "       {" + CustomProductLabelModel.PRODUCT + "} = ?product";

    private static final String FIND_EXPIRED_LABEL_QUERY =
            SELECT +
            "WHERE  {" + CustomProductLabelModel.VALIDITYDATE + "} < ?now";

    private static final String FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_AND_NULLCUSTOMER_QUERY =
            SELECT +
            "WHERE  ({" + CustomProductLabelModel.CUSTOMER + "} = ?customer OR {" + CustomProductLabelModel.CUSTOMER + "} is null) AND " +
            "       {" + CustomProductLabelModel.PRODUCT + "} = ?product";

    private static final String FIND_LABELS_BY_PRODUCT_QUERY =
            SELECT +
            "WHERE  {" + CustomProductLabelModel.PRODUCT + "} = ?product";
    @Override
    public List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customer, ProductModel product) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_QUERY);
        query.addQueryParameter("customer", customer);
        query.addQueryParameter("product", product);

        return findResult(query);
    }
    @Override
    public List<CustomProductLabelModel> findExpired(final Date now){
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_EXPIRED_LABEL_QUERY );
        query.addQueryParameter("now", now);

        return findResult(query);
    }

    @Override
    public List<CustomProductLabelModel> findByCustomerAndProductAndNullCustomer(CustomerModel customer, ProductModel product) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_LABELS_BY_CUSTOMER_AND_PRODUCT_AND_NULLCUSTOMER_QUERY);
        query.addQueryParameter("customer", customer);
        query.addQueryParameter("product", product);

        return findResult(query);
    }

    @Override
    public List<CustomProductLabelModel> findByProduct(ProductModel product) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_LABELS_BY_PRODUCT_QUERY);
        query.addQueryParameter("product", product);

        return findResult(query);
    }

    public List<CustomProductLabelModel> findResult(final FlexibleSearchQuery query){
        final SearchResult<CustomProductLabelModel> result = getFlexibleSearchService().search(query);

        if(Objects.nonNull(result) && CollectionUtils.isNotEmpty(result.getResult())) {
            return result.getResult();
        }
        LOG.warn("Unable to find results for query");

        return Collections.emptyList();
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
