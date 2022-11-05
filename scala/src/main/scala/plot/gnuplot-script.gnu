#!/usr/bin/gnuplot --persist
set datafile separator ','
set xlabel "x"
set ylabel "y"
plot\
'/home/alexander/Education/ITMO/2-nd_year/comp-math/lab5/scala/tables/lagrange_polynomial.csv' using 1:2 smooth csplines notitle with lines lt 1,\
'/home/alexander/Education/ITMO/2-nd_year/comp-math//lab5/scala/tables/newton_polynomial.csv' using 1:2 smooth csplines notitle with lines lt 2,\
'/home/alexander/Education/ITMO/2-nd_year/comp-math/lab5/scala/input/input.csv' using 1:2 with points pointsize 1 pointtype 7 lt 1 title "исходные точки",\
'tables/for_point.csv'  using 1:2 with points pointsize 1 pointtype 7 lt 3 title "считали для этой точки"
pause -1