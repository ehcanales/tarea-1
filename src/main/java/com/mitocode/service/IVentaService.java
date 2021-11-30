package com.mitocode.service;

import java.util.List;
import com.mitocode.model.Producto;
import com.mitocode.model.Venta;

public interface IVentaService extends ICRUD<Venta, Integer>{

	Venta registrarTransaccional(Venta venta, List<Producto> productos) throws Exception;

}
