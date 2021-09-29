package com.ardctraining.facades.product;

import com.ardctraining.facades.product.data.CustomProductLabelData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import java.util.List;

public interface ArdctrainingProductFacade extends ProductFacade {

    /**
     *  get customer labels by products
     * @param product
     * @return
     */
    List<CustomProductLabelData> getCustomLabels(final String product);
}
