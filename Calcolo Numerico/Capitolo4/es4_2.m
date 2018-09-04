function y = newton(xi,fi,x)
% function y = newton(xi,fi,x)
% Implementa il calcolo del polinomio interpolante di grado n in forma di Newton
%  xi vettore delle ascisse di interpolazione
%  fi vettore dei valori della funzione in x
%  x vettore dei punti in cui valutare il polinomio
%  y vettore dei valori del polinomio valutato sui punti x.
if length(xi) ~= length(fi)
    error('xi e fi hanno lunghezza diversa!')
end
dd = diff_div(xi, fi);
y = dd(length(dd));
for k = length(dd)-1:-1:1
    y = y*(x-xi(k))+dd(k);
end
end

function [fi] = diff_div(xi, fi)
for i = 1:length(xi) - 1
    for j = length(xi):-1:i+1
        fi(j) = (fi(j) - fi(j-1))/(xi(j)-xi(j-i));
    end
end
end