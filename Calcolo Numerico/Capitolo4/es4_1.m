function y = lagrange(xi, fi, x)
% function y = lagrange( xi, fi, x )
% xi vettore dei punti di ascissa
% fi vettore dei valori di f(x)
% x vettore di punti in cui calcolare f(x)
% y valore di f(x)
if length(xi) ~= length(fi)
    error('xi e fi hanno lunghezza diversa, deve essere uguale')
end
n = length(xi)-1;
m = length(x);
y = zeros(size(x));
for i=1:m
    for j=1:n+1
        p = 1;
        for k=1:n+1
            if j~=k
                p = p*(x(i)-xi(k))/(xi(j)-xi(k));
            end
        end
        y(i) = y(i)+fi(j)*p;
    end
end
return
end