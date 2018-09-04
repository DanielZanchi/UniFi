% primo esempio
A1 = [4 2 2; 1 1 2; 2 3 4; 2 1 1];
b1= [4 5 4 1]';

x1 = soluzione_es_8(algoritmo_3_8(A1), b1);
x1_Ab = A1\b1;

% secondo esempio
A2 = [1 2; 2 6; 4 3];
b2 = [4 5 6]';
x2 = soluzione_es_8(algoritmo_3_8(A2), b2);
x2_Ab = A2\b2;

function [b] = soluzione_es_8(A, b)
    [m,n] = size(A);
    Qt = eye(m);
    for i=1:n
        Qt= [eye(i-1) zeros(i-1,m-i+1); zeros(i-1, m-i+1)' (eye(m-i+1)-(2/norm([1; A(i+1:m, i)], 2)^2)*([1; A(i+1:m, i)]*[1 A(i+1:m, i)']))]*Qt;
    end
    b = sistema_triang_sup(triu(A(1:n, :)), Qt(1:n, :)*b);
end

% fattorizzazione QR di householder
function A = algoritmo_3_8(A)
    [m,n] = size(A);
    for i=1:n
        alpha = norm(A(i:m, i));
        if alpha==0
            error("il rango non e' massimo")
        end
        if A(i,i)>=0
            alpha = -alpha;
        end
        v = A(i,i) - alpha;
        A(i,i) = alpha;
        A(i+1:m,i) = A(i+1:m,i)/v;
        beta = -v/alpha;
        A(i:m,i+1:n) = A(i:m, i+1:n) - (beta*[1; A(i+1:m,i)])*([1 A(i+1:m,i)']*A(i:m,i+1:n));
    end
end

function [b] = sistema_triang_sup(A, b)
	for j=length(A):-1:1
		b(j)=b(j)/A(j,j);
		for i=1:j-1
			b(i)=b(i)-A(i,j)*b(j);
		end
	end
end
