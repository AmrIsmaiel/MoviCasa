package com.Som3a.movicasa.Data.models;

import java.util.List;

public class MovieResponse {

    private List<Results> results;

    private Dates dates;

    private String page;

    private String total_pages;

    private String total_results;

    public MovieResponse (List<Results> results , Dates dates , String total_pages , String total_results){
        super();
        this.results = results;
        this.dates = dates ;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public List<Results> getResults ()
    {
        return results;
    }

    public void setResults (List<Results> results)
    {
        this.results = results;
    }

    public Dates getDates ()
    {
        return dates;
    }

    public void setDates (Dates dates)
    {
        this.dates = dates;
    }

    public String getPage ()
    {
        return page;
    }

    public void setPage (String page)
    {
        this.page = page;
    }

    public String getTotal_pages ()
    {
        return total_pages;
    }

    public void setTotal_pages (String total_pages)
    {
        this.total_pages = total_pages;
    }

    public String getTotal_results ()
    {
        return total_results;
    }

    public void setTotal_results (String total_results)
    {
        this.total_results = total_results;
    }



    public class Dates
    {
        private String minimum;

        private String maximum;

        public String getMinimum ()
        {
            return minimum;
        }

        public void setMinimum (String minimum)
        {
            this.minimum = minimum;
        }

        public String getMaximum ()
        {
            return maximum;
        }

        public void setMaximum (String maximum)
        {
            this.maximum = maximum;
        }

    }



}
