function y = hermite(xi, fi, f1i, x)
% function y = hermite(xi, fi, f1i, x)
%   xi vettore delle ascisse
%   fi vettore delle valutazioni di f(x)
%   f1i vettore delle valutazioni di f'(x)
%   x punto da valutare
%   y vettore riscrito con le differenze divise
if length(xi) ~= length(fi) || length(f1i) ~= length(fi)
    error('xi, fi e f1x devono avere la stessa lunghezza')
end
% combino opportunamente i vettori
xih = zeros(length(xi)*2, 1);
fih = zeros(length(fi)*2, 1);
for i = 1:length(xi)
    xih(i+i-1) = xi(i);
    xih(i+i) = xi(i);
    fih(i+i-1) = fi(i);
    fih(i+i) = f1i(i);
end
ddh = diffDiviseHermite(xih, fih);
y = ddh(1);
for i = 2 : length(dd)
    prod = ddh(i);
    for j=1:i-1
        prod = prod*(x-xih(j));
    end
    y = y + prod;
end
end

function [fi] = diffDiviseHermite(xi, fi)
%  function [f] = diffDiviseHermite(x, f)
%  xi vettore delle ascisse
%  fi vettore con f(x0), f'(x0),..., f(xn), f'(xn)
%  fi vettore riscrito con le differenze divise
    n = length(xi)-1;
    for i = n:-2:3
        fi(i) = (fi(i)-fi(i-2))/(xi(i)-xi(i-1));
    end
    for j = 2:n
        for i = n+1:-1:j+1
            fi(i) = (fi(i)-fi(i-1))/(xi(i)-xi(i-j));
        end
    end
return
end