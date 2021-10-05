package com.ardctraining.core.product.service;

import com.ardctraining.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;
import java.util.List;

public interface CustomProductLabelService {
    /**
     * finds a custom label by customer and product
     * @param customer
     * @param product
     * @return
     */
    List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customer, ProductModel product);

    /**
     *  finds all expired labels
     * @return
     */
    List<CustomProductLabelModel> findExpired();

    /**
     * find
     * @param customer
     * @param product
     * @return
     */
    List<CustomProductLabelModel> findByCustomerAndProductAndNullCustomer(CustomerModel customer, ProductModel product);

    /**
     *
     * @param product
     * @return
     */
    List<CustomProductLabelModel> findByProduct(ProductModel product);
}
