package Paginador;


import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElemenPorPaginas;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        numElemenPorPaginas = page.getSize();
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;

        int desde, hasta;

        if (totalPaginas <= numElemenPorPaginas){
        desde = 1;
        hasta = totalPaginas;
        }else{
            if(paginaActual <= numElemenPorPaginas/2){
                desde = 1;
                hasta = numElemenPorPaginas;
            } else if (paginaActual>= totalPaginas - numElemenPorPaginas/2){
                desde = totalPaginas - numElemenPorPaginas + 1;
                hasta = numElemenPorPaginas;
            } else{
                desde = paginaActual -numElemenPorPaginas/2;
                hasta = numElemenPorPaginas;
            }

        }
        for(int i=0; i < hasta;i++){
            paginas.add(new PageItem(desde + i , paginaActual == desde+i));
        }

    }

    public String getUrl() {
        return url;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }
    public boolean isFirst(){return  page.isFirst();}
    public boolean isLast(){
        return  page.isLast();
    }
    public boolean isHasnext(){
        return page.hasNext();
    }
    public boolean isHasPrevious(){return page.hasPrevious();}
}


